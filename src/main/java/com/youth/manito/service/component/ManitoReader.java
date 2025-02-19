package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Manito;
import com.youth.manito.domain.entity.ManitoGroup;
import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.domain.repository.ManitoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManitoReader {

    private final ManitoRepository manitoRepository;

    private final ManitoGroupReader manitoGroupReader;

    public Manito getByTeamAndReceiver(final Team team, final User receiver) {
        ManitoGroup manitoGroup = manitoGroupReader.getByTeam(team);
        return manitoRepository.findByManitoGroupAndReceiver(manitoGroup, receiver)
                .orElseThrow(() -> new EntityNotFoundException("해당 팀과 유저에 대한 매니또 정보가 없습니다."));
    }
}
