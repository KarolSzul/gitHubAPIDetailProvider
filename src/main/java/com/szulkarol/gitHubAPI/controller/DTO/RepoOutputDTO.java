package com.szulkarol.gitHubAPI.controller.DTO;

import java.util.List;

public record RepoOutputDTO(String repositoryName, String login, List<Branch> branches) {

    public record Branch(String branchName, String sha) {}
}
