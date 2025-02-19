package com.youth.manito.service;

import com.youth.manito.controller.dto.ManitoResultResponse;
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
import com.youth.manito.service.component.ManitoReader;
import com.youth.manito.service.component.TeamReader;
import com.youth.manito.service.component.UserReader;
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
    }

    private void checkTeam(User user, Team team) {
        if (!team.getId().equals(user.getTeam().getId())) {
            throw new BadRequestException("유저의 팀이 아닙니다.");
        }
    }

    public ManitoResultResponse getManitoResults(final Long teamId, final Long receiverId) {
        Team team = teamReader.getById(teamId);
        User receiver = userReader.getById(receiverId);

        return ManitoResultResponse.from(manitoReader.getByTeamAndReceiver(team, receiver));
    }
}
