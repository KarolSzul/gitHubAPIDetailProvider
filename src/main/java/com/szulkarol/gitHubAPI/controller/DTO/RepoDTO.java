package com.szulkarol.gitHubAPI.controller.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class RepoDTO {

    private  String name;
    private  Owner owner;
    private  boolean fork;

    private String branches_url;

    public String getBranches_url() {
        return branches_url;
    }

    public RepoDTO(String name, Owner owner, boolean fork) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
    }

    public RepoDTO() {
    }

    public boolean isFork() {
        return fork;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }

    public static class Owner {
        private String login;

        public Owner(String login) {
            this.login = login;
        }

        public Owner() {
        }

        public String getLogin() {
            return login;
        }

    }

}


