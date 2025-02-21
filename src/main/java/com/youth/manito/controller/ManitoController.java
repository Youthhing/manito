package com.youth.manito.controller;

import com.youth.manito.controller.dto.ManitoResultOpenRequest;
import com.youth.manito.controller.dto.ManitosResponse;
import com.youth.manito.controller.dto.ResultVoteRequest;
import com.youth.manito.service.ManitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams/{teamId}/manito-group/manito")
@RequiredArgsConstructor
public class ManitoController {

    private final ManitoService manitoService;

    @GetMapping("/receivers")
    public ResponseEntity<ManitosResponse> getManitoReceiviers(@PathVariable("teamId") Long teamId, @RequestParam("userId") Long userId) {
        return ResponseEntity.ok().body(manitoService.getManitoReceivers(teamId, userId));
    }

    @PutMapping("/{manitoId}")
    public ResponseEntity<Void> openManito(@PathVariable("teamId") Long teamId, @PathVariable("manitoId") Long manitoId,
                                           @RequestParam("userId") Long userId,
                                           @RequestBody ManitoResultOpenRequest request) {
        manitoService.resultOpen(manitoId, userId, request.giverOpen());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{manitoId}/result-vote")
    public ResponseEntity<Void> voteManito(@PathVariable("teamId") Long teamId,
                                           @RequestParam("manitoId") Long manitoId,
                                           @RequestBody ResultVoteRequest request) {
        manitoService.resultVote(teamId, manitoId, request.userId(), request.agree());
        return ResponseEntity.ok().build();
    }
}
