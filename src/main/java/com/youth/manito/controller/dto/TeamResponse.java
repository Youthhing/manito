package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.Team;

public record TeamResponse(
        Long id,
        String name
) {
    public static TeamResponse of(Team team) {
        return new TeamResponse(team.getId(), team.getName());
    }
}
