package com.youth.manito.controller;

import com.youth.manito.controller.dto.TeamUserResponse;
import com.youth.manito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<TeamUserResponse> getTeamUsers(@RequestParam("teamId") Long teamId) {
        return ResponseEntity.ok(userService.getTeamUsers(teamId));
    }
}
