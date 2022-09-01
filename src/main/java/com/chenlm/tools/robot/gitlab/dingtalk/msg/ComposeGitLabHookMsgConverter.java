package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.chenlm.tools.robot.gitlab.GitLabHookMsgConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ComposeGitLabHookMsgConverter implements GitLabHookMsgConverter {
    private final List<GitLabHookMsgConverter> gitLabHookMsgConverters;

    @Override
    public Optional<String> toMsg(String gitLabHook) {
        return gitLabHookMsgConverters.stream()
                .map(a -> a.toMsg(gitLabHook))
                .filter(Optional::isPresent)
                .findFirst().orElse(Optional.empty());
    }
}
