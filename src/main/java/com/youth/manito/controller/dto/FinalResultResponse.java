package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Team;
import java.util.List;

public record FinalResultResponse(
        Long teamId,
        String teamName,
        Long manitoGroupId,
        List<ResultResponse> results
) {
    public static FinalResultResponse of(Team team, ManitoGroup manitoGroup, List<ResultResponse> resultResponses) {
        return new FinalResultResponse(
                team.getId(),
                team.getName(),
                manitoGroup.getId(),
                resultResponses
        );
    }
}
