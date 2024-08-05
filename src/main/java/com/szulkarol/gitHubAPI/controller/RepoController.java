package com.szulkarol.gitHubAPI.controller;

import com.szulkarol.gitHubAPI.service.ApiErrorResponse;
import com.szulkarol.gitHubAPI.service.RepoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping(value = "/repositoriesInfo")
public class RepoController {

    private final RepoService repoService;

    public RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    @GetMapping(value = "/user/{username}")
    public ResponseEntity<?> getRepositoryDetails(@PathVariable("username") String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(repoService.getRepositoryDetails(username));
        }
        catch (HttpClientErrorException httpClientErrorException)  {
            return ResponseEntity
                    .status(httpClientErrorException.getStatusCode().value())
                    .body(new ApiErrorResponse(httpClientErrorException.getStatusCode().value(),
                            "Something went wrong. Please check if the username is correct or try again later."));
        }
        catch (HttpServerErrorException httpServerErrorException) {
            return ResponseEntity
                    .status(httpServerErrorException.getStatusCode().value())
                    .body(new ApiErrorResponse(httpServerErrorException.getStatusCode().value(),
                            "An unexpected error occurred. Please try again later"));
        }
        catch (RuntimeException exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "An unexpected error occurred. Please try again later"));
        }
    }
}