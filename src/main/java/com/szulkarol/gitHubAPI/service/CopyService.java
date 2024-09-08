//package com.szulkarol.gitHubAPI.service;
//
//import com.szulkarol.gitHubAPI.controller.DTO.RepoDTO;
//import com.szulkarol.gitHubAPI.controller.DTO.RepoOutputDTO;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class CopyService {
//
//    private String url;
//
//    private RestTemplate restTemplate;
//
//    public CopyService(String url, RestTemplate restTemplate) {
//        this.url = url;
//        this.restTemplate = restTemplate;
//    }
//
//    public List<RepoOutputDTO> getRepositoryDetails(String username) {
//        List<RepoDTO> repoDTOS = getRawRepositoryDetails(username);
//        return repoDTOS
//                .stream()
//                .filter(repo -> !repo.fork())
//                .map(repo -> {
//                List<RepoOutputDTO.Branch> branches = mapBranchesDetails(repo.getBranchesUrl());
//                RepoOutputDTO repoOutputDTO = new RepoOutputDTO(repo.getName(), repo.getOwner().getLogin(), branches);
//                return repoOutputDTO;
//        }).collect(Collectors.toList());
//
//
//    }
//
//    private List<RepoOutputDTO.Branch> mapBranchesDetails(String url) {
//        String newUrl = url.replace("/branches","");
//        List<BranchDTO> branchDTOList = getBranchDTOsFromRepository(newUrl);
//        if (branchDTOList != null) {
//            return branchDTOList
//                    .stream()
//                    .map(branch -> new RepoOutputDTO.Branch(branch.getName(), branch.getCommit().getSha()))
//                    .collect(Collectors.toList());
//        }
//        else {
//            return new ArrayList<>();
//        }
//    }
//
//    private String getUrl(String username) {
//        return String.format(url, username);
//    }
//
//    private List<RepoDTO> getRawRepositoryDetails(String username) {
//        try {
//            RepoDTO[] repos = restTemplate.getForObject(getUrl(username), RepoDTO[].class);
//            return Arrays.asList(repos);
//        } catch (HttpClientErrorException httpClientErrorException) {
//            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
//                    "GitHub repository error: " + httpClientErrorException.getMessage());
//        }
//        catch (HttpServerErrorException httpServerErrorException) {
//            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
//                    "GitHub repository error: " + httpServerErrorException.getMessage());
//        }
//        catch (RuntimeException exception) {
//            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
//                    "An unexpected error occurred: " + exception.getMessage());
//        }
//
//    }
//
//    private List<BranchDTO> getBranchDTOsFromRepository(String url) {
//        BranchDTO[] branchDTOs = restTemplate.getForObject(url, BranchDTO[].class);
//        return Arrays.asList(branchDTOs);
//    }
//
//}
