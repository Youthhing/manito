package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.Vote;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VoteChecker {

    private final ManitoReader manitoReader;

    @Transactional
    public void gradeVotes(final List<Vote> votes, final ManitoGroup manitoGroup) {
        Map<Long, User> giverByReceiverId = manitoReader.getByManitoGroup(manitoGroup).stream()
                .collect(Collectors.toUnmodifiableMap(manito -> manito.getReceiver().getId(), Manito::getGiver));

        List<Vote> correctVotes = votes.stream()
                .filter(vote -> giverByReceiverId.get(vote.getReceiver().getId())
                        .getId().equals(vote.getGiver().getId()))
                .toList();
        correctVotes.forEach(Vote::correct);
    }
}
