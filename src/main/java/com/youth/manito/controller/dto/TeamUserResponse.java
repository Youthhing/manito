package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.Team;
import java.util.List;

public record TeamUserResponse(
        Long teamId,
        String teamName,
        List<UserResponse> users
) {
    public static TeamUserResponse of(Team team, List<UserResponse> users) {
        return new TeamUserResponse(team.getId(), team.getName(), users);
    }
}
