package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TeamReader {

    private final TeamRepository teamRepository;

    public Team getByCode(final String code) {
        return teamRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 팀입니다."));
    }
}
