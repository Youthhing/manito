package com.youth.manito.controller;

import com.youth.manito.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class ViewController {

    private final TeamService teamService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/team")
    public String team(@RequestParam("code") String code, Model model) {
        model.addAttribute("team", teamService.getByCode(code));
        return "team";
    }
}
