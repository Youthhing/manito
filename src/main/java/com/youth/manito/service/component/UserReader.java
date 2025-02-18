package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReader {

    private final UserRepository userRepository;

    public List<User> getAllByTeam(final Team team) {
        return userRepository.findAllByTeam(team);
    }
}
