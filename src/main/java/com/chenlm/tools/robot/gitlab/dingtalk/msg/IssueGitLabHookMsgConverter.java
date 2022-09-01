package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.chenlm.tools.robot.gitlab.GitLabHookMsgConverter;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IssueGitLabHookMsgConverter implements GitLabHookMsgConverter {
    private final SpringTemplateEngine springTemplateEngine;
    private final Gson gson;


    @Override
    public Optional<String> toMsg(String gitLabHook) {
        String kind = JsonPath.read(gitLabHook, "$.object_kind");
        if (!"issue".equals(kind)) {
            return Optional.empty();
        }
        Context context = new Context();
        context.setVariable("gitlab", gson.fromJson(gitLabHook, Map.class));
        return Optional.ofNullable(springTemplateEngine.process("dingtalk/issue/issue", context));
    }
}
