package com.youth.manito.domain.repository;

import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByTeam(Team team);

    Optional<User> findByEmail(String email);
}
