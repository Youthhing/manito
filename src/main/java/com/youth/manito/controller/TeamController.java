package com.youth.manito.controller;

import com.youth.manito.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/{id}/manitoes")
    public ResponseEntity<Void> createManitoes(@PathVariable("id") Long id) {
        teamService.createManitoes(id);
        return ResponseEntity.ok().build();
    }
}
