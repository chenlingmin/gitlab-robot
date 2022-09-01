package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import com.chenlm.tools.robot.gitlab.GitLabHookMsgConverter;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractTemplateGitLabHookMsgConverter implements GitLabHookMsgConverter {

    private final SpringTemplateEngine springTemplateEngine;
    private final Gson gson;

    protected abstract String supportKind();
    protected abstract String template(String gitLabHook);

    @Override
    public Optional<String> toMsg(String gitLabHook) {
        String kind = JsonPath.read(gitLabHook, "$.object_kind");
        if (!supportKind().equals(kind)) {
            return Optional.empty();
        }
        Context context = new Context();
        context.setVariable("gitlab", gson.fromJson(gitLabHook, Map.class));
        String template = template(gitLabHook);
        if (StringUtils.isNotBlank(template)) {
            return Optional.ofNullable(springTemplateEngine.process(template, context));
        } else {
            return Optional.empty();
        }
    }
}
