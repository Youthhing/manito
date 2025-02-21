package com.youth.manito.controller.dto;

public record ResultVoteRequest(
        Long userId,
        boolean agree
) {
}
