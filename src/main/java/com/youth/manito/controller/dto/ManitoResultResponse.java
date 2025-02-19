package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.Manito;
import lombok.Builder;

@Builder
public record ManitoResultResponse(
        Long manitoId,
        Long receiverId,
        String receiverName,
        Long giverId,
        String giverName,
        Long missionId,
        String missionContent
) {
    public static ManitoResultResponse from(final Manito manito) {
        return ManitoResultResponse.builder()
                .manitoId(manito.getId())
                .receiverId(manito.getReceiver().getId())
                .receiverName(manito.getReceiver().getName())
                .giverId(manito.getGiver().getId())
                .giverName(manito.getGiver().getName())
                .missionId(manito.getMission().getId())
                .missionContent(manito.getMission().getContents())
                .build();
    }
}
