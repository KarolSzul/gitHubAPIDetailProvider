package com.szulkarol.gitHubAPI.controller.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public record RepoDTO(String name, Owner owner, boolean fork,
                      @JsonProperty("branches_url") @SerializedName("branches_url") String branchesUrl) {

    public record Owner(String login) {}
}
