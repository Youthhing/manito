package com.youth.manito.service.component;

import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.UserVoteGroup;
import com.youth.manito.domain.entity.Vote;
import com.youth.manito.domain.repository.VoteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VoteReader {

    private final VoteRepository voteRepository;

    @Transactional(readOnly = true)
    public List<Vote> getAllByUserVoteGroupsAndReceiver(final List<UserVoteGroup> userVoteGroups, final User receiver) {
        List<Long> userVoteGroupIds = userVoteGroups.stream()
                .map(UserVoteGroup::getId)
                .toList();
        return voteRepository.findAllByUserVoteGroupIdInAndReceiver(userVoteGroupIds, receiver);
    }
}
