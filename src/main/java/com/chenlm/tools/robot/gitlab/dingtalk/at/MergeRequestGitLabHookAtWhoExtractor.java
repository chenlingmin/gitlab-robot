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
public class MergeRequestGitLabHookAtWhoExtractor implements GitLabHookAtWhoExtractor {

    private final UserRepository userRepository;

    @Override
    public List<String> extract(String gitLabHook) {
        String kind = JsonPath.read(gitLabHook, "$.object_kind");

        if (!"merge_request".equals(kind)) {
            return Collections.emptyList();
        }

        String action = JsonPath.read(gitLabHook, "$.object_attributes.action");


        Optional<Long> authorId = Optional.ofNullable(JsonPath.read(gitLabHook, "$.object_attributes.author_id"))
                .map(Object::toString)
                .map(Long::parseLong);

        Optional<Long> assigneeId = Optional.ofNullable(JsonPath.read(gitLabHook, "$.object_attributes.assignee_id"))
                .map(Object::toString)
                .map(Long::parseLong);

        List<Long> atList = new ArrayList<>();

        if ("close".equals(action)) {
            authorId.ifPresent(atList::add);
        } else if ("open".equals(action)) {
            assigneeId.ifPresent(atList::add);
        } else if ("update".equals(action)) {
            authorId.ifPresent(atList::add);
            assigneeId.ifPresent(atList::add);
        } else if ("merge".equals(action)) {
            authorId.ifPresent(atList::add);
        } else if ("reopen".equals(action)) {
            assigneeId.ifPresent(atList::add);
        } else {
            log.warn("未知的 action {}", action);
        }

        return atList.stream().map(userRepository::getDingtalkUserByGtitlabUserId)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
