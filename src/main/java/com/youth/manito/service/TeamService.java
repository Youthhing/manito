package com.youth.manito.service;

import com.youth.manito.controller.dto.TeamResponse;
import com.youth.manito.service.component.TeamReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamReader teamReader;

    public TeamResponse getByCode(final String code) {
        return TeamResponse.of(teamReader.getByCode(code));
    }
}
