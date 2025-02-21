package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.User;
import lombok.Builder;

@Builder
public record ResultResponse(
        Long receiverId,
        String receiverName,
        Long giverId,
        String giverName,
        long missionAgreeCount,
        long denominator
) {
    public static ResultResponse of(final User receiver, final User giver, long missionAgreeCount, long denominator) {
        return new ResultResponse(
                receiver.getId(),
                receiver.getName(),
                giver.getId(),
                giver.getName(),
                missionAgreeCount,
                denominator
        );
    }
}
