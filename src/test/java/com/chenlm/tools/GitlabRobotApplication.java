package com.chenlm.tools;


import com.chenlm.tools.robot.gitlab.config.GitlabConfiguration;
import com.chenlm.tools.robot.gitlab.dingtalk.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootApplication
public class GitlabRobotApplication {
    public static void main(String[] args) {
        SpringApplication.run(GitlabRobotApplication.class, args);
    }

    @Bean
    @Primary
    public UserRepository mockUserRepository(GitlabConfiguration.GitLabApiProperties gitLabApiProperties) {
        return new UserRepository() {
            @Override
            public Optional<String> getDingtalkUserByGitlabUserName(String gitlabUserName) {
                return Optional.ofNullable(gitLabApiProperties.getGitlabToDingtalkUsers().get(gitlabUserName));
            }

            @Override
            public Optional<String> getDingtalkUserByGtitlabUserId(Long gitlabUserId) {
                Map<Long, String> mockUser = new HashMap<Long, String>() {{
                    put(68L, "heiniu");
                    put(51L, "daniu");
                    put(38L, "heiniu");
                }};

                return Optional.ofNullable(mockUser.get(gitlabUserId))
                        .map(this::getDingtalkUserByGitlabUserName)
                        .filter(Optional::isPresent)
                        .map(Optional::get);
            }
        };
    }
}
