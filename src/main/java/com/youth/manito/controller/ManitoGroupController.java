package com.youth.manito.controller;

import com.youth.manito.controller.dto.FinalResultResponse;
import com.youth.manito.controller.dto.ManitoResponse;
import com.youth.manito.controller.dto.ManitoVoteResultResponse;
import com.youth.manito.controller.dto.RevealRateResponse;
import com.youth.manito.controller.dto.UserVoteResultsResponse;
import com.youth.manito.controller.dto.VoteManitoGroupRequest;
import com.youth.manito.service.ManitoGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class ManitoGroupController {

    private final ManitoGroupService manitoGroupService;

    @PutMapping("/{id}/manito-groups/vote")
    public ResponseEntity<Void> voteManitoResults(@PathVariable("id") Long teamId,
                                                  @RequestBody VoteManitoGroupRequest request) {
        manitoGroupService.voteManitoResults(teamId, request.userId(), request.submitted(), request.votes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{teamId}/manito-groups/manito")
    public ResponseEntity<ManitoResponse> getManito(@PathVariable("teamId") Long teamId, @RequestParam("receiverId") Long receiverId) {
        return ResponseEntity.ok().body(manitoGroupService.getManito(teamId, receiverId));
    }

    @GetMapping("/{teamId}/manito-groups/vote/results")
    public ResponseEntity<ManitoVoteResultResponse> getManitoResults(@PathVariable("teamId") Long teamId, @RequestParam("receiverId") Long receiverId) {
        return ResponseEntity.ok().body(manitoGroupService.getManitoResults(teamId, receiverId));
    }

    @GetMapping("/{teamId}/manito-groups/vote/revealRate")
    public ResponseEntity<RevealRateResponse> getRevealRate(@PathVariable("teamId") Long teamId, @RequestParam("receiverId") Long receiverId) {
        return ResponseEntity.ok().body(manitoGroupService.getRevealRate(teamId, receiverId));
    }

    @GetMapping("/{teamId}/manito-groups/vote/results/sense-king")
    public ResponseEntity<UserVoteResultsResponse> getSenseKing(@PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok().body(manitoGroupService.getSenseKingResults(teamId));
    }

    @GetMapping("/{teamId}/manito-groups/vote/results/final")
    public ResponseEntity<FinalResultResponse> getFinalResults(@PathVariable("teamId") Long teamId) {
        return ResponseEntity.ok().body(manitoGroupService.getFinalResults(teamId));
    }
}
