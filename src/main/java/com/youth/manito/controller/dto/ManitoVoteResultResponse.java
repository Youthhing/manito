package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.Vote;
import java.util.List;

public record ManitoVoteResultResponse(
        Long manitoGroupId,
        Long receiverId,
        String receiverName,
        List<UserVoteGroupResponse> votes
) {
    public static ManitoVoteResultResponse of(final ManitoGroup manitoGroup, final User receiver, final List<Vote> votes) {
        return new ManitoVoteResultResponse(
                manitoGroup.getId(),
                receiver.getId(),
                receiver.getName(),
                votes.stream()
                        .map(UserVoteGroupResponse::from)
                        .toList()
        );
    }
}
