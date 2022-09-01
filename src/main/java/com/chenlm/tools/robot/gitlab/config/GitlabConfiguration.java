package com.chenlm.tools.robot.gitlab.config;

import com.chenlm.tools.robot.gitlab.dingtalk.ConfigUserRepository;
import com.chenlm.tools.robot.gitlab.dingtalk.UserRepository;
import lombok.Data;
import org.gitlab4j.api.GitLabApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class GitlabConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GitLabApi gitLabApi(GitLabApiProperties gitLabApiProperties) {
        return new GitLabApi(gitLabApiProperties.getUrl(), gitLabApiProperties.getAccessToken())
                .withRequestTimeout(1000, 1000);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserRepository userRepository(GitLabApiProperties gitLabApiProperties, GitLabApi gitLabApi) {
        return new ConfigUserRepository(gitLabApiProperties, gitLabApi);
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "gitlab")
    public static class GitLabApiProperties {
        /**
         * gitlab url
         */
        private String url;
        /**
         * gitlab accessToken
         */
        private String accessToken;
        /**
         * gitlab user 与钉钉手机号码对应关系
         */
        private Map<String, String> gitlabToDingtalkUsers;
    }
}
