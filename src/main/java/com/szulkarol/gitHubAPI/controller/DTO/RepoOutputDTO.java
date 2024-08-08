package com.szulkarol.gitHubAPI.controller.DTO;

import java.util.List;

public class RepoOutputDTO {

    private String repositoryName;
    private String login;
    private List<Branch> branches;

    public RepoOutputDTO(String name, String login, List<Branch> branches) {
        this.repositoryName = name;
        this.login = login;
        this.branches = branches;
    }

    public RepoOutputDTO() {
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getLogin() {
        return login;
    }

    public List<Branch> getBranches() {
        return branches;
    }


    public static class Branch {
        private  String branchName;
        private  String sha;

        public Branch(String branchName, String sha) {
            this.branchName = branchName;
            this.sha = sha;
        }

        public Branch() {
        }

        public String getBranchName() {
            return branchName;
        }

        public String getSha() {
            return sha;
        }

    }

}
