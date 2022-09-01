package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;


/**
 * 评论消息推送
 */

@Component
@Slf4j
public class MergeRequestGitLabHookMsgConverter extends AbstractTemplateGitLabHookMsgConverter {
    public MergeRequestGitLabHookMsgConverter(SpringTemplateEngine springTemplateEngine, Gson gson) {
        super(springTemplateEngine, gson);
    }

    @Override
    protected String supportKind() {
        return "merge_request";
    }

    @Override
    protected String template(String gitLabHook) {
        String state = JsonPath.read(gitLabHook, "$.object_attributes.action");
        if (StringUtils.isNotBlank(state)) {
            return "dingtalk/merge_request/" + state;
        }
        log.warn("merge_request 未实现 state = {}", state);
        return null;
    }

}
