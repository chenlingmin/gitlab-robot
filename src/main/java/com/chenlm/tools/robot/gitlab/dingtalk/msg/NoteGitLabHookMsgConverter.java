package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.SpringTemplateEngine;


/**
 * 评论消息推送
 */

@Component
public class NoteGitLabHookMsgConverter extends AbstractTemplateGitLabHookMsgConverter {

    public NoteGitLabHookMsgConverter(SpringTemplateEngine springTemplateEngine, Gson gson) {
        super(springTemplateEngine, gson);
    }

    @Override
    protected String supportKind() {
        return "note";
    }

    @Override
    protected String template(String gitLabHook) {
        return "dingtalk/note/note";
    }

}
