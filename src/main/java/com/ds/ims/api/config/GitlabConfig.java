package com.ds.ims.api.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GitlabConfig {
    @Value("${gitlab.url}")
    String gitlabUrl;

    @Value("${gitlab.accessToken}")
    String accessToken;

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitlabUrl, accessToken);
    }
}
