package com.ds.ims.api.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация GitLab API
 */
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GitlabConfig {
    /**
     * URL GitLab
     */
    @Value("${gitlab.url}")
    String gitlabUrl;

    /**
     * Токен доступа к GitLab
     */
    @Value("${gitlab.accessToken}")
    String accessToken;

    /**
     * Bean для работы с GitLab API
     */
    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitlabUrl, accessToken);
    }
}
