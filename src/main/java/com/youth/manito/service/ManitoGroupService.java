package com.youth.manito.service;

import com.youth.manito.controller.dto.ManitoResponse;
import com.youth.manito.controller.dto.ManitoVoteResultResponse;
import com.youth.manito.controller.dto.RevealRateResponse;
import com.youth.manito.controller.dto.UserVoteResultResponse;
import com.youth.manito.controller.dto.UserVoteResultsResponse;
import com.youth.manito.controller.dto.VoteManitoRequest;
import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.UserVoteGroup;
import com.youth.manito.domain.entity.Vote;
import com.youth.manito.domain.repository.ManitoGroupRepository;
import com.youth.manito.domain.repository.UserVoteGroupRepository;
import com.youth.manito.domain.repository.VoteRepository;
import com.youth.manito.exception.BadRequestException;
import com.youth.manito.service.component.ManitoGroupReader;
import com.youth.manito.service.component.ManitoReader;
import com.youth.manito.service.component.TeamReader;
import com.youth.manito.service.component.UserReader;
import com.youth.manito.service.component.VoteChecker;
import com.youth.manito.service.component.VoteReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManitoGroupService {

    private final TeamReader teamReader;

    private final UserReader userReader;

    private final ManitoReader manitoReader;

    private final ManitoGroupReader manitoGroupReader;

    private final VoteReader voteReader;

    private final VoteChecker voteChecker;

    private final UserVoteGroupRepository userVoteGroupRepository;

    private final VoteRepository voteRepository;

    private final ManitoGroupRepository manitoGroupRepository;

    @Transactional
    public void voteManitoResults(final Long teamId, final Long userId, final boolean submitted, final List<VoteManitoRequest> voteRequest) {
        Team team = teamReader.getById(teamId);
        User user = userReader.getById(userId);
        checkTeam(user, team);

        ManitoGroup manitoGroup = manitoGroupRepository.findByTeam(team).orElseGet(() -> ManitoGroup.of(team));
        manitoGroupRepository.save(manitoGroup);

        UserVoteGroup userVoteGroup = userVoteGroupRepository.findByUserId(userId)
                .orElseGet(() -> userVoteGroupRepository.save(UserVoteGroup.of(user, manitoGroup)));

        if (userVoteGroup.isSubmitted()) {
            throw new BadRequestException("이미 투표를 완료했습니다.");
        }

        List<Long> userIds = voteRequest.stream()
                .map(vote -> List.of(vote.giverId(), vote.receiverId()))
                .flatMap(List::stream)
                .toList();

        Map<Long, User> userById = userReader.getAllByIds(userIds).stream()
                .collect(Collectors.toUnmodifiableMap(User::getId, Function.identity()));

        List<Vote> votes = voteRequest.stream()
                .map(vote -> Vote.of(userById.get(vote.giverId()), userById.get(vote.receiverId()), userVoteGroup))
                .toList();

        userVoteGroup.updateSubmitted(submitted);
        userVoteGroup.updateVotes(votes);
        voteRepository.saveAll(votes);

        if (submitted) {
            voteChecker.gradeVotes(votes, manitoGroup);
        }
    }

    private void checkTeam(User user, Team team) {
        if (!team.getId().equals(user.getTeam().getId())) {
            throw new BadRequestException("유저의 팀이 아닙니다.");
        }
    }

    public ManitoResponse getManito(final Long teamId, final Long receiverId) {
        Team team = teamReader.getById(teamId);
        User receiver = userReader.getById(receiverId);

        return ManitoResponse.from(manitoReader.getByTeamAndReceiver(team, receiver));
    }

    public ManitoVoteResultResponse getManitoResults(final Long teamId, final Long receiverId) {
        Team team = teamReader.getById(teamId);
        User receiver = userReader.getById(receiverId);
        ManitoGroup manitoGroup = manitoGroupReader.getByTeam(team);

        List<UserVoteGroup> userVoteGroups = userVoteGroupRepository.findAllByManitoGroup(manitoGroup);
        List<Vote> votes = voteReader.getAllByUserVoteGroupsAndReceiver(userVoteGroups, receiver);
        votes.sort(Comparator.comparing(vote -> !vote.getUserVoteGroup().getUser().getId().equals(receiver.getId())));

        return ManitoVoteResultResponse.of(manitoGroup, receiver, votes);
    }

    public RevealRateResponse getRevealRate(final Long teamId, final Long receiverId) {
        Team team = teamReader.getById(teamId);
        User receiver = userReader.getById(receiverId);
        ManitoGroup manitoGroup = manitoGroupReader.getByTeam(team);

        List<UserVoteGroup> userVoteGroups = userVoteGroupRepository.findAllByManitoGroup(manitoGroup);
        List<Vote> votes = voteReader.getAllByUserVoteGroupsAndReceiver(userVoteGroups, receiver);

        long totalVotes = votes.size();
        long correctVotes = votes.stream().filter(Vote::isResult).count();

        return RevealRateResponse.of(totalVotes, correctVotes);
    }

    @Transactional(readOnly = true)
    public UserVoteResultsResponse getSenseKingResults(final Long teamId) {
        Team team = teamReader.getById(teamId);
        ManitoGroup manitoGroup = manitoGroupReader.getByTeam(team);
        Map<Long, User> userById = userReader.getAllByTeam(team).stream()
                .collect(Collectors.toUnmodifiableMap(User::getId, Function.identity()));

        List<UserVoteGroup> userVoteGroups = userVoteGroupRepository.findAllByManitoGroup(manitoGroup);

        Map<Long, List<Vote>> votesByUserId = voteReader.getAllByUserVoteGroups(userVoteGroups).stream()
                .collect(Collectors.groupingBy(vote -> vote.getUserVoteGroup().getUser().getId()));

        List<UserVoteResultResponse> userVoteResultResponses = votesByUserId.entrySet().stream()
                .map(entry -> UserVoteResultResponse.of(userById.get(entry.getKey()), entry.getValue()))
                .sorted(Comparator.comparing(UserVoteResultResponse::correctCount).reversed())
                .toList();

        return UserVoteResultsResponse.of(team, userVoteResultResponses);
    }
}
