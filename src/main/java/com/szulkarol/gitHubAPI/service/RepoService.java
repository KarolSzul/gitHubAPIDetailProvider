package com.szulkarol.gitHubAPI.service;

import com.szulkarol.gitHubAPI.controller.DTO.BranchDTO;
import com.szulkarol.gitHubAPI.controller.DTO.RepoDTO;
import com.szulkarol.gitHubAPI.controller.DTO.RepoOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepoService {

    private final String url;
    private final RestTemplate restTemplate;

    public RepoService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
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
            RepoDTO[] repoDTOS = restTemplate.getForObject(getUrl(username), RepoDTO[].class);
            return Arrays.asList(repoDTOS);
        } catch (HttpClientErrorException httpClientErrorException) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                "GitHub repository error: " + httpClientErrorException.getMessage());
        }
        catch (HttpServerErrorException httpServerErrorException) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                "GitHub repository error: " + httpServerErrorException.getMessage());
        }
        catch (RuntimeException exception) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + exception.getMessage());
        }
    }

    private String getUrl(String username) {
        return String.format(url, username);
    }

    private List<RepoOutputDTO.Branch> mapBranchesDetails(String url) {
        String newUrl = url.replace("{/branch}", "");

        BranchDTO[] branchDTO = checkMapBranchesDetailsExceptions(newUrl);

        if (branchDTO != null) {
            return Arrays.stream(branchDTO)
                    .map(branch -> new RepoOutputDTO.Branch(branch.getName(), branch.getCommit().getSha()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private BranchDTO[] checkMapBranchesDetailsExceptions(String url) {
        try {
           return restTemplate.getForObject(url, BranchDTO[].class);
        } catch (RestClientException restClientException) {
            return null;
        }
    }


}