package com.chenlm.tools.robot.gitlab.dingtalk.at;

import lombok.RequiredArgsConstructor;
import com.chenlm.tools.robot.gitlab.GitLabHookAtWhoExtractor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ComposeGitLabHookAtWhoExtractor implements GitLabHookAtWhoExtractor {

    final List<GitLabHookAtWhoExtractor> gitLabHookAtWhoExtractors;

    @Override
    public List<String> extract(String gitLabHook) {
        return gitLabHookAtWhoExtractors
                .stream().map(a -> a.extract(gitLabHook))
                .flatMap(Collection::stream)
                .distinct().collect(Collectors.toList());
    }
}
