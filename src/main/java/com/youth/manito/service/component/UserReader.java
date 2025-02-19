package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public User getByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 이메일이 없습니다."));
    }

    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당하는 유저가 없습니다."));
    }

    public List<User> getAllByIds(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }
}
