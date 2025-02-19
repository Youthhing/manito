package com.youth.manito.controller.dto;

public record RevealRateResponse(
        long numerator,
        long denominator
) {
    public static RevealRateResponse of(long totalVotes, long correctVotes) {
        return new RevealRateResponse(correctVotes, totalVotes);
    }
}
