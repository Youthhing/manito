package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.User;

public record UserResponse(
        Long id,
        String email,
        String name,
        Long teamId
) {
    public static UserResponse from(final User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getTeam().getId());
    }
}
