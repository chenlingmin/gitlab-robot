package com.chenlm.tools.robot.gitlab.dingtalk.at;

import com.chenlm.tools.robot.gitlab.dingtalk.UserRepository;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.chenlm.tools.robot.gitlab.GitLabHookAtWhoExtractor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class IssueGitLabHookAtWhoExtractor implements GitLabHookAtWhoExtractor {
    private final UserRepository userRepository;

    @Override
    public List<String> extract(String gitLabHook) {
        String kind = JsonPath.read(gitLabHook, "$.object_kind");
        if (!"issue".equals(kind)) {
            return Collections.emptyList();
        }
        String action = JsonPath.read(gitLabHook, "$.object_attributes.action");

        Optional<Long> authorId = Optional.ofNullable(JsonPath.read(gitLabHook, "$.object_attributes.author_id"))
                .map(Object::toString)
                .map(Long::parseLong);

        Optional<Long> assigneeId = Optional.ofNullable(JsonPath.read(gitLabHook, "$.object_attributes.assignee_id"))
                .map(Object::toString)
                .map(Long::parseLong);

        List<Long> gitlabUsers = new ArrayList<>();
        if ("close".equals(action)) {
            authorId.ifPresent(gitlabUsers::add);
        } else if ("open".equals(action)) {
            assigneeId.ifPresent(gitlabUsers::add);
        } else if ("reopen".equals(action)) {
            authorId.ifPresent(gitlabUsers::add);
            assigneeId.ifPresent(gitlabUsers::add);
        } else {
            log.warn("");
        }
        return gitlabUsers.stream()
                .map(userRepository::getDingtalkUserByGtitlabUserId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
