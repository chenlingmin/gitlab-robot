package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * 提交代码消息推送
 */
@Component
@Slf4j
public class PushGitLabHookMsgConverter extends AbstractTemplateGitLabHookMsgConverter {

    public PushGitLabHookMsgConverter(SpringTemplateEngine springTemplateEngine, Gson gson) {
        super(springTemplateEngine, gson);
    }

    @Override
    protected String supportKind() {
        return "push";
    }

    @Override
    protected String template(String gitLabHook) {
        String before = JsonPath.read(gitLabHook, "$.before");
        String after = JsonPath.read(gitLabHook, "$.after");
        int commitsCount = JsonPath.read(gitLabHook, "$.total_commits_count");
        if (commitsCount == 0 && "0000000000000000000000000000000000000000".equals(before)) {
            return "dingtalk/push/create_branch";
        } else if (commitsCount == 0 && "0000000000000000000000000000000000000000".equals(after)) {
            return "dingtalk/push/delete_branch";
        } else if (commitsCount > 0) {
            return "dingtalk/push/commit";
        } else {
            log.warn("位置的 push 类型, msg: {}", gitLabHook);
            return null;
        }
    }

}
