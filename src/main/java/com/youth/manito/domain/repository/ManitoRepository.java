package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManitoRepository extends JpaRepository<Manito, Long> {
    Optional<Manito> findByManitoGroupAndReceiver(ManitoGroup manitoGroup, User receiver);

    List<Manito> findAllByManitoGroup(ManitoGroup manitoGroup);
}
