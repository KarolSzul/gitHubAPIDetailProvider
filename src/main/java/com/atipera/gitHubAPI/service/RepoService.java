package com.atipera.gitHubAPI.service;

import com.atipera.gitHubAPI.controller.DTO.BranchDTO;
import com.atipera.gitHubAPI.controller.DTO.RepoDTO;
import com.atipera.gitHubAPI.controller.DTO.RepoOutputDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepoService {

    @Value("${repositories.url.property}")
    private String url;
    private RestTemplate restTemplate;

    public RepoService() {
        this.restTemplate = new RestTemplate();
    }

    private String getUrl(String username) {
        return String.format(url, username);
    }

    public List<RepoOutputDTO> getRepositoryDetails(String username) {
        return getRawRepositoriesDetails(username)
                .stream()
                .filter(repo -> !repo.isFork())
                .map(repo -> {
                    List<RepoOutputDTO.Branch> branches = mapBranchesDetails(repo.getBranches_url());
                    RepoOutputDTO repoOutputDTO = new RepoOutputDTO(repo.getName(), repo.getOwner().getLogin(), branches);
                    return repoOutputDTO;
                })
                .collect(Collectors.toList());

    }

    private List<RepoDTO> getRawRepositoriesDetails(String username) {
        try {
            return Arrays.stream(restTemplate.getForObject(getUrl(username), RepoDTO[].class)).toList();

        } catch (RestClientException restClientException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private List<RepoOutputDTO.Branch> mapBranchesDetails(String url) {
        String newUrl = url.replace("{/branch}", "");
        BranchDTO[] branchDTO = null;
        try {
            branchDTO = restTemplate.getForObject(newUrl, BranchDTO[].class);
            return Arrays.asList(branchDTO)
                    .stream()
                    .map(branch -> new RepoOutputDTO.Branch(branch.getName(), branch.getCommit().getSha()))
                    .collect(Collectors.toList());

        } catch (RestClientException restClientException) {
            return null;
        }
    }


}