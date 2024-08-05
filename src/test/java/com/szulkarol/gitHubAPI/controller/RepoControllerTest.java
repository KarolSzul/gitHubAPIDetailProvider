package com.szulkarol.gitHubAPI.controller;


import com.szulkarol.gitHubAPI.controller.DTO.RepoOutputDTO;
import com.szulkarol.gitHubAPI.service.RepoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(RepoController.class)
class RepoControllerTest {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RepoService repoService;

	@Test
	public void testGetRepositoryDetails() throws Exception {

		List<RepoOutputDTO> mockRepoOutputDTO = new ArrayList<>();
		List<RepoOutputDTO.Branch> branches1 = new ArrayList<>();
		List<RepoOutputDTO.Branch> branches2 = new ArrayList<>();

		RepoOutputDTO.Branch branch1 = new RepoOutputDTO.Branch("BranchName1", "ExampleSha1");
		RepoOutputDTO.Branch branch2 = new RepoOutputDTO.Branch("BranchName2", "ExampleSha2");
		RepoOutputDTO.Branch branch3 = new RepoOutputDTO.Branch("BranchName3", "ExampleSha3");
		RepoOutputDTO.Branch branch4 = new RepoOutputDTO.Branch("BranchName4", "ExampleSha4");

		branches1.add(branch1);
		branches1.add(branch2);
		branches2.add(branch3);
		branches2.add(branch4);

		mockRepoOutputDTO.add(new RepoOutputDTO("ExampleName1", "ExampleLogin1", branches1));
		mockRepoOutputDTO.add(new RepoOutputDTO("ExampleName2", "ExampleLogin2", branches2));

		Mockito.when(repoService.getRepositoryDetails("KarolSzul")).thenReturn(mockRepoOutputDTO);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/repositoriesInfo/user/KarolSzul")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].repositoryName").value("ExampleName1"))
				.andExpect(jsonPath("$[1].repositoryName").value("ExampleName2"))
				.andExpect(jsonPath("$[2].repositoryName").doesNotExist())
				.andExpect(jsonPath("$[0].login").value("ExampleLogin1"))
				.andExpect(jsonPath("$[1].login").value("ExampleLogin2"))
				.andExpect(jsonPath("$[0].branches[0].branchName").value("BranchName1"))
				.andExpect(jsonPath("$[1].branches[1].branchName").value("BranchName4"))
				.andExpect(jsonPath("$[1].branches[0].sha").value("ExampleSha3"))
				.andExpect(jsonPath("$[1].branches[0].sha").value("ExampleSha3"));

	}

	@Test
	public void checkClientErrorExceptionTest() throws Exception {

		String userName = "KarolSzghfgdhgfhul123456789";

		Mockito.when(repoService.getRepositoryDetails(userName))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,
						"Something went wrong."));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/repositoriesInfo/user/" + userName)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.code").value(404))
				.andExpect(jsonPath("$.message")
						.value("Something went wrong. Please check if the username is correct or try again later."));
	}

	@Test
	public void checkHttpServerErrorExceptionTest() throws Exception {

		String userName = "KarolSzul";

		Mockito.when(repoService.getRepositoryDetails(userName))
				.thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Something went wrong."));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/repositoriesInfo/user/" + userName)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.code").value(500))
				.andExpect(jsonPath("$.message")
						.value("An unexpected error occurred. Please try again later"));

	}

	@Test
	public void checkUndefinedExceptionTest() throws Exception{

		String userName = "KarolSzul";

		Mockito.when(repoService.getRepositoryDetails(userName))
				.thenThrow(new RuntimeException("Something went wrong."));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/repositoriesInfo/user/" + userName)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(jsonPath("$.code").value(500))
				.andExpect(jsonPath("$.message")
						.value("An unexpected error occurred. Please try again later"));

	}

}
