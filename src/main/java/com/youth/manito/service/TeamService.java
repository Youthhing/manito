package com.youth.manito.service;

import com.youth.manito.controller.dto.TeamResponse;
import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Mission;
import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.repository.ManitoGroupRepository;
import com.youth.manito.domain.repository.MissionRepository;
import com.youth.manito.exception.BadRequestException;
import com.youth.manito.service.component.EmailSender;
import com.youth.manito.service.component.MissionReader;
import com.youth.manito.service.component.TeamReader;
import com.youth.manito.service.component.UserReader;
import com.youth.manito.util.EmailUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamReader teamReader;

    private final UserReader userReader;

    private final MissionReader missionReader;

    private final ManitoGroupRepository manitoGroupRepository;

    private final MissionRepository missionRepository;

    private final EmailSender emailSender;

    public TeamResponse getByCode(final String code) {
        return TeamResponse.of(teamReader.getByCode(code));
    }

    @Transactional
    public void createManitoes(final Long teamId) {
        Team team = teamReader.getById(teamId);
        List<User> users = userReader.getAllByTeam(team);

        ManitoGroup manitoGroup = ManitoGroup.of(team);
        Map<User, User> receiverByGiver = matchManito(users);

        List<Manito> manitos = receiverByGiver.entrySet().stream()
                .map(entry -> Manito.of(entry.getKey(), entry.getValue(), manitoGroup))
                .toList();

        List<Mission> missions = missionReader.getRandomMissions(manitos.size()).stream().toList();

        IntStream.range(0, manitos.size())
                .forEach(i -> manitos.get(i).assignMission(missions.get(i)));

        missionRepository.saveAll(missions);
        manitoGroupRepository.save(manitoGroup);

        manitos.forEach(manito -> emailSender.sendEmail(manito.getGiver(), EmailUtil.getMatchManitoMessageBody(manito) , EmailUtil.MATCH_MANITO_SUBJECT));
    }

    public Map<User, User> matchManito(final List<User> users) {
        if (users == null || users.size() < 2) {
            throw new BadRequestException("매칭할 사용자가 최소 2명 이상 필요합니다.");
        }

        List<User> deranged = new ArrayList<>(users);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int n = deranged.size();

        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i);
            Collections.swap(deranged, i, j);
        }

        Map<User, User> matchResult = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            matchResult.put(users.get(i), deranged.get(i));
        }
        return matchResult;
    }
}
