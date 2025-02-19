package com.youth.manito.controller.dto;

import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.UserVoteGroup;
import com.youth.manito.domain.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UserVoteGroupResponse(
        Long voterId,
        String voterName,
        UserVoteResult giver,
        UserVoteResult receiver,
        boolean isCorrect
) {

    public static UserVoteGroupResponse from(final Vote vote) {
        UserVoteGroup userVoteGroup = vote.getUserVoteGroup();
        return new UserVoteGroupResponse(
                userVoteGroup.getUser().getId(),
                userVoteGroup.getUser().getName(),
                UserVoteResult.from(vote.getGiver()),
                UserVoteResult.from(vote.getReceiver()),
                vote.isResult()
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UserVoteResult {
        private Long userId;
        private String userName;

        static UserVoteResult from(final User user) {
            return new UserVoteResult(user.getId(), user.getName());
        }
    }
}
