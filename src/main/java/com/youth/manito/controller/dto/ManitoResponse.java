package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.Manito;
import lombok.Builder;

@Builder
public record ManitoResponse(
        Long manitoId,
        Long receiverId,
        String receiverName,
        boolean isGiverOpen,
        Long giverId,
        String giverName,
        Long missionId,
        String missionContent
) {
    public static ManitoResponse from(final Manito manito) {
        ManitoResponseBuilder builder = ManitoResponse.builder()
                .manitoId(manito.getId())
                .receiverId(manito.getReceiver().getId())
                .receiverName(manito.getReceiver().getName())
                .isGiverOpen(manito.isGiverOpen());

        if (manito.isGiverOpen()) {
            builder.giverId(manito.getGiver().getId())
                    .giverName(manito.getGiver().getName())
                    .missionId(manito.getMission().getId())
                    .missionContent(manito.getMission().getContents());
        }

        return builder.build();
    }
}
