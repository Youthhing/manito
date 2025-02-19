package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Team;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManitoGroupRepository extends JpaRepository<ManitoGroup, Long> {
    Optional<ManitoGroup> findByTeam(Team team);
}
