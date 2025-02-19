package com.youth.manito.controller.dto;

import java.util.List;

public record VoteManitoGroupRequest(
        Long userId,
        boolean submitted,
        List<VoteManitoRequest> votes
) {
}
