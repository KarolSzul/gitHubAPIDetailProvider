package com.szulkarol.gitHubAPI.controller.DTO;


public class BranchDTO {

    private String name;
    private Commit commit;

    public BranchDTO(String name, Commit commit) {
        this.name = name;
        this.commit = commit;
    }

    public BranchDTO() {
    }

    public String getName() {
        return name;
    }

    public Commit getCommit() {
        return commit;
    }

    public static class Commit {
        private String sha;

        public Commit(String sha) {
            this.sha = sha;
        }

        public Commit() {
        }

        public String getSha() {
            return sha;
        }

        @Override
        public String toString() {
            return "Commit{" +
                    "sha='" + sha + '\'' +
                    '}';
        }
    }

}
