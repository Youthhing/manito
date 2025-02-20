package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.Team;
import java.util.List;

public record UserVoteResultsResponse(
        Long teamId,
        String teamName,
        List<UserVoteResultResponse> results
) {

    public static UserVoteResultsResponse of(Team team, List<UserVoteResultResponse> userVoteResultResponses) {
        return new UserVoteResultsResponse(
                team.getId(),
                team.getName(),
                userVoteResultResponses
        );
    }
}
