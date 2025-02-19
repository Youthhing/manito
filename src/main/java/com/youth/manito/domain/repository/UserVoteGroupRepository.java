package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.UserVoteGroup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVoteGroupRepository extends JpaRepository<UserVoteGroup, Long> {
    Optional<UserVoteGroup> findByUserId(Long userId);
}
