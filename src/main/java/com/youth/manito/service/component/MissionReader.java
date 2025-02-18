package com.youth.manito.service.component;

import com.youth.manito.domain.entity.Mission;
import com.youth.manito.domain.repository.MissionRepository;
import com.youth.manito.exception.BadRequestException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MissionReader {

    private final MissionRepository missionRepository;

    @Transactional(readOnly = true)
    public Set<Mission> getRandomMissions(final int count) {
        if (missionRepository.count() < count) {
            throw new BadRequestException("요청한 미션 개수가 존재하는 미션 개수보다 많습니다.");
        }

        List<Long> missionIds = missionRepository.findAllMissionIds();

        Collections.shuffle(missionIds);
        List<Long> selectedIds = missionIds.stream()
                .limit(count)
                .toList();

        return missionRepository.findByIdIn(selectedIds).stream().collect(Collectors.toUnmodifiableSet());
    }
}
