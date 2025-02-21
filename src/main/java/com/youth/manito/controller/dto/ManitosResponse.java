package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.Team;
import java.util.List;

public record ManitosResponse(
        Long teamId,
        String teamName,
        List<ManitoResponse> manitos
) {
    public static ManitosResponse of(Team team, List<ManitoResponse> manitoResponses) {
        return new ManitosResponse(
                team.getId(),
                team.getName(),
                manitoResponses
        );
    }
}
