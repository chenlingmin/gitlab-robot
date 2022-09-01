package com.chenlm.tools.robot.gitlab.dingtalk.at;

import com.chenlm.tools.robot.gitlab.dingtalk.UserRepository;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import com.chenlm.tools.robot.gitlab.GitLabHookAtWhoExtractor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class NoteGitLabHookAtWhoExtractor implements GitLabHookAtWhoExtractor {

    private final UserRepository userRepository;

    private final static Pattern pattern = Pattern.compile("@([^ ]+?)( |$)");

    @Override
    public List<String> extract(String gitLabHook) {
        String kind = JsonPath.read(gitLabHook, "$.object_kind");

        if (!"note".equals(kind)) {
            return Collections.emptyList();
        }
        String note = JsonPath.read(gitLabHook, "$.object_attributes.note");
        if (StringUtils.isBlank(note)) {
            return Collections.emptyList();
        }

        Matcher matcher = pattern.matcher(note);
        List<String> atUsernames = new ArrayList<>();
        while (matcher.find()) {
            atUsernames.add(matcher.group(1));
        }

        List<Long> userIds = new ArrayList<>();
        String readable = JsonPath.read(gitLabHook, "$.object_attributes.noteable_type");
        if ("Issue".equals(readable)) {
            Optional.ofNullable(JsonPath.read(gitLabHook, "$.issue.author_id"))
                    .map(Object::toString)
                    .map(Long::parseLong)
                    .ifPresent(userIds::add);
        } else if ("MergeRequest".equals(readable)) {
            Optional.ofNullable(JsonPath.read(gitLabHook, "$.merge_request.author_id"))
                    .map(Object::toString)
                    .map(Long::parseLong)
                    .ifPresent(userIds::add);
        }

        return Stream.concat(
                userIds.stream().map(userRepository::getDingtalkUserByGtitlabUserId),
                atUsernames.stream().map(userRepository::getDingtalkUserByGitlabUserName)
        ).filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

    }
}
