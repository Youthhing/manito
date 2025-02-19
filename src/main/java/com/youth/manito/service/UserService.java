package com.youth.manito.service;

import com.youth.manito.controller.dto.TeamUserResponse;
import com.youth.manito.controller.dto.UserResponse;
import com.youth.manito.domain.entity.Team;
import com.youth.manito.domain.entity.User;
import com.youth.manito.exception.BadRequestException;
import com.youth.manito.service.component.TeamReader;
import com.youth.manito.service.component.UserReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReader userReader;

    private final TeamReader teamReader;

    public UserResponse getByEmailAndKey(final String email, final String sessionKey){
        User user = userReader.getByEmail(email);
        if(!user.getSessionKey().equals(sessionKey)){
            throw new BadRequestException("잘못된 접근입니다.");
        }

        return UserResponse.from(user);
    }

    public TeamUserResponse getTeamUsers(final Long teamId){
        Team team = teamReader.getById(teamId);
        List<UserResponse> users = userReader.getAllByTeam(team).stream()
                .map(UserResponse::from)
                .toList();
        return TeamUserResponse.of(team, users);
    }
}
