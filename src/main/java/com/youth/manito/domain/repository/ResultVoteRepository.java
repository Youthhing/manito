package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ResultVote;
import com.youth.manito.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultVoteRepository extends JpaRepository<ResultVote, Long> {
    boolean existsByUserAndManito(User user, Manito manito);
}
