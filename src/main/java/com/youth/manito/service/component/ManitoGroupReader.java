package com.youth.manito.service.component;

import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.repository.ManitoGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManitoGroupReader {

    private final ManitoGroupRepository manitoGroupRepository;

    public ManitoGroup getByTeam(final Team team) {
        return manitoGroupRepository.findByTeam(team)
                .orElseThrow(() -> new EntityNotFoundException("해당 팀에 대한 매니또 그룹 정보가 없습니다."));
    }
}
