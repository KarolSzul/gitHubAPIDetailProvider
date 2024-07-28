package com.atipera.gitHubAPI.controller.DTO;

import java.util.ArrayList;
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

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<Branch> branches) {
        this.branches = branches;
    }

    public static class Branch {
        private String branchName;
        private String sha;

        public Branch(String branchName, String sha) {
            this.branchName = branchName;
            this.sha = sha;
        }

        public Branch() {
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }
    }

}
