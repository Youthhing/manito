package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.entity.Vote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByUserVoteGroupIdInAndReceiver(List<Long> userVoteGroupIds, User receiver);
}
