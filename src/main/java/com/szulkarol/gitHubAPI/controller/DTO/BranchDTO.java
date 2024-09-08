package com.szulkarol.gitHubAPI.controller.DTO;

public record BranchDTO(String name, Commit commit) {

    public static record Commit (String sha) {}
}
