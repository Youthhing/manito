package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByCode(String code);
}
