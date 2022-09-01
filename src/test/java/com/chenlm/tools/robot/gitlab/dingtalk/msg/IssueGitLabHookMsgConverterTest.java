package com.chenlm.tools.robot.gitlab.dingtalk.msg;

import com.chenlm.tools.robot.gitlab.dingtalk.GitLabController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@SpringBootTest
class IssueGitLabHookMsgConverterTest {

    @Autowired
    private IssueGitLabHookMsgConverter issueGitLabHookMsgConverter;
    @Autowired
    private GitLabController gitLabController;

    @Test
    void toMsg() throws IOException {
        testPushMsg("gitlab/issue/close.json", "templates/dingtalk/result/issue/issue_close.md");
        testPushMsg("gitlab/issue/open.json", "templates/dingtalk/result/issue/issue_open.md");
        testPushMsg("gitlab/issue/reopen.json", "templates/dingtalk/result/issue/issue_reopen.md");
    }

//    @Ignore
//    @Test
//    void hello() throws IOException {
//        gitLabController.textPush(getFileString("gitlab/push/commit.json"), "f07577115af2dad35cdc6487b8eda573c495fb91642b5da1419d5c581400c79c");
//    }

    void testPushMsg(String request, String result) throws IOException{
        String gitlabMsg = getFileString(request);
        Optional<String> commit = issueGitLabHookMsgConverter.toMsg(gitlabMsg);
        Assertions.assertTrue(commit.isPresent());
        Assertions.assertEquals(getFileString(result), commit.get());
    }


    private String getFileString(String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + filePath) ;
        return new String(Files.readAllBytes(file.toPath()));
    }

}
