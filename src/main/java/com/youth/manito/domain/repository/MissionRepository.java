package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.Mission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT m.id FROM Mission m where m.deleted = false")
    List<Long> findAllMissionIdsDeletedFalse();

    List<Mission> findByIdIn(List<Long> ids);
}
