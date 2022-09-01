package com.chenlm.tools.robot.gitlab.dingtalk;


import java.util.Optional;

public interface UserRepository {

    Optional<String> getDingtalkUserByGitlabUserName(String gitlabUserName);

    Optional<String> getDingtalkUserByGtitlabUserId(Long gitlabUserId);

}
