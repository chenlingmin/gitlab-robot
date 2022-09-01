package com.chenlm.tools.robot.gitlab.dingtalk;

import lombok.extern.slf4j.Slf4j;
import com.chenlm.tools.robot.gitlab.config.GitlabConfiguration.GitLabApiProperties;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.UserApi;
import org.gitlab4j.api.models.User;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ConfigUserRepository implements UserRepository {
    private final GitLabApi gitLabApi;
    private final Map<Long, String> gitlabUserId2DingtalkUserMap;
    private final Map<String, String> gitlabUsername2DingtalkUserMap;

    public ConfigUserRepository(GitLabApiProperties gitLabApiProperties, GitLabApi gitLabApi) {
        this.gitLabApi = gitLabApi;
        this.gitlabUsername2DingtalkUserMap = gitLabApiProperties.getGitlabToDingtalkUsers();
        this.gitlabUserId2DingtalkUserMap = new HashMap<>();
        refreshGitlabUser();
    }

    @Override
    public Optional<String> getDingtalkUserByGitlabUserName(String gitlabUserName) {
        return Optional.ofNullable(gitlabUsername2DingtalkUserMap.get(gitlabUserName));
    }

    @Override
    public Optional<String> getDingtalkUserByGtitlabUserId(Long gitlabUserId) {
        return Optional.ofNullable(gitlabUserId2DingtalkUserMap.get(gitlabUserId));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void refreshGitlabUser() {
        UserApi userApi = gitLabApi.getUserApi();
        try {
            gitlabUserId2DingtalkUserMap.clear();
            for (User user : userApi.getUsers()) {
                String dingtalkUser = gitlabUsername2DingtalkUserMap.get(user.getUsername());
                if (StringUtils.isNotBlank(dingtalkUser)) {
                    gitlabUserId2DingtalkUserMap.put(user.getId(), dingtalkUser);
                }
            }
        } catch (GitLabApiException e) {
            log.warn("更新 gitlab user 失败", e);
        }
    }


}
