package com.atipera.gitHubAPI.controller;
;
import com.atipera.gitHubAPI.controller.DTO.RepoOutputDTO;
import com.atipera.gitHubAPI.service.RepoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/repositoriesInfo")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @GetMapping(value = "/user/{username}")
    public List<RepoOutputDTO> getRepositoryDetails(@PathVariable("username") String username) {
        return repoService.getRepositoryDetails(username);
    }
}