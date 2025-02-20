package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.Vote;
import java.util.List;

public record UserVoteResultResponse(
        Long userId,
        String userName,
        long correctCount,
        long totalCount
) {
    public static UserVoteResultResponse of(final User user, final List<Vote> votes) {
        return new UserVoteResultResponse(
                user.getId(),
                user.getName(),
                votes.stream().filter(Vote::isResult).count(),
                votes.size()
        );
    }
}
