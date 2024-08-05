package com.szulkarol.gitHubAPI;

import com.szulkarol.gitHubAPI.service.RepoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RepoService repoService(@Value("${repositories.url.property}") String url, RestTemplate restTemplate) {
        return new RepoService(url, restTemplate);
    }





}
