package com.szulkarol.gitHubAPI.service;

import com.szulkarol.gitHubAPI.controller.DTO.BranchDTO;
import com.szulkarol.gitHubAPI.controller.DTO.RepoDTO;
import com.szulkarol.gitHubAPI.controller.DTO.RepoOutputDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@SpringBootTest
public class RepoServiceTest {

    @Autowired
    private RepoService repoService;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    public void checkResponseFromFirstRepository() throws IOException {

<<<<<<< HEAD
        // Mocking the RepoDTO array
=======
        // Given

        String userName = "KarolSzul";
        String repoName= "car-rental-repository";

        // When
>>>>>>> 130d7ee (Refactored testing and some methods)

        Mockito.when(restTemplate.getForObject(getRepoUrl(userName), RepoDTO[].class))
                .thenReturn(getRepoDTOArrayFromJSON("src/test/resources/RawRepositoryDetails.json"));

        Mockito.when(restTemplate.getForObject(getBranchUrl(userName, repoName), BranchDTO[].class))
                .thenReturn(getBranchDTOArrayFromJSON("src/test/resources/CarRentalRepositoryBranches.json"));

        List<RepoOutputDTO> resultList = repoService.getRepositoryDetails(userName);

        RepoOutputDTO repoOutputDTO1 = resultList.get(0);

        List<RepoOutputDTO.Branch> branchDTOList1 = repoOutputDTO1.getBranches();

        // Then

        Assertions.assertEquals(repoOutputDTO1.getRepositoryName(), "car-rental-repository");
        Assertions.assertEquals(repoOutputDTO1.getLogin(), "KarolSzul");

        Assertions.assertEquals(branchDTOList1.get(0).getBranchName(), "main");
        Assertions.assertEquals(branchDTOList1.get(0).getSha(), "0bb732e187e4ab7536fb8d45aee977dafc7fb1b5");

    }

    @Test
    public void checkResponseFromSecondRepository() throws IOException {

        // Given

        String userName = "KarolSzul";
        String repoName= "CodeWars";

        Mockito.when(restTemplate.getForObject(getRepoUrl(userName), RepoDTO[].class))
                .thenReturn(getRepoDTOArrayFromJSON("src/test/resources/RawRepositoryDetails.json"));

        // When

        Mockito.when(restTemplate.getForObject(getBranchUrl(userName, repoName), BranchDTO[].class))
                .thenReturn(getBranchDTOArrayFromJSON("src/test/resources/CodeWarsBranches.json"));

        List<RepoOutputDTO> resultList = repoService.getRepositoryDetails(userName);
        RepoOutputDTO repoOutputDTO1 = resultList.get(1);
        List<RepoOutputDTO.Branch> branchDTOList1 = repoOutputDTO1.getBranches();

        // Then

        Assertions.assertEquals(repoOutputDTO1.getRepositoryName(), "CodeWars");
        Assertions.assertEquals(repoOutputDTO1.getLogin(), "KarolSzul");

        Assertions.assertEquals(branchDTOList1.get(0).getBranchName(), "main");
        Assertions.assertEquals(branchDTOList1.get(0).getSha(), "5048f37301a6cbd2e0202c2dc317c515af947328");
    }

    private String getRepoUrl(String userName) {
        return "https://api.github.com/users/" + userName + "/repos";
    }

    private String getBranchUrl(String userName, String repo) {
        return "https://api.github.com/repos/" + userName + "/" + repo + "/branches";
    }

    private RepoDTO[] getRepoDTOArrayFromJSON(String path) throws IOException {
        String json = Files.readString(Paths.get(path));
        Gson gson = new Gson();
        Type arrayType = new TypeToken<RepoDTO[]>() {}.getType();
        return gson.fromJson(json, arrayType);
    }

    private BranchDTO[] getBranchDTOArrayFromJSON(String path) throws  IOException {
        String branchJson = Files.readString(Paths.get(path));
        Gson gson = new Gson();
        Type branchArrayType = new TypeToken<BranchDTO[]>() {}.getType();
        return gson.fromJson(branchJson, branchArrayType);
    }

}
