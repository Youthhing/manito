package com.youth.manito.service;

import com.youth.manito.controller.dto.ManitoResponse;
import com.youth.manito.controller.dto.ManitosResponse;
import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.exception.BadRequestException;
import com.youth.manito.service.component.ManitoGroupReader;
import com.youth.manito.service.component.ManitoReader;
import com.youth.manito.service.component.TeamReader;
import com.youth.manito.service.component.UserReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManitoService {

    private final TeamReader teamReader;

    private final UserReader userReader;

    private final ManitoGroupReader manitoGroupReader;

    private final ManitoReader manitoReader;

    public ManitosResponse getManitoReceivers(final Long teamId, final Long userId) {
        Team team = teamReader.getById(teamId);
        User user = userReader.getById(userId);

        if (!user.getTeam().getId().equals(team.getId())) {
            throw new BadRequestException("user is not in the team");
        }

        ManitoGroup manitoGroup = manitoGroupReader.getByTeam(team);
        List<Manito> manitos = manitoReader.getByManitoGroup(manitoGroup);

        List<ManitoResponse> manitoResponses = manitos.stream()
                .map(ManitoResponse::from)
                .toList();

        return ManitosResponse.of(team, manitoResponses);
    }

}
