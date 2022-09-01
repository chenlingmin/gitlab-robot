package com.chenlm.tools.robot.gitlab.dingtalk.msg;

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
class MergeRequestGitLabHookMsgConverterTest {

    @Autowired
    private MergeRequestGitLabHookMsgConverter mergeRequestGitLabHookMsgConverter;

    @Test
    public void toMsg() throws IOException {
        testMergeRequest("gitlab/merge_request/open.json", "templates/dingtalk/result/merge_request/open.md");
        testMergeRequest("gitlab/merge_request/close.json", "templates/dingtalk/result/merge_request/close.md");
        testMergeRequest("gitlab/merge_request/merge.json", "templates/dingtalk/result/merge_request/merge.md");
        testMergeRequest("gitlab/merge_request/update.json", "templates/dingtalk/result/merge_request/update.md");
        testMergeRequest("gitlab/merge_request/reopen.json", "templates/dingtalk/result/merge_request/reopen.md");
    }

    void testMergeRequest(String request, String result) throws IOException {
        String gitlabMsg = getFileString(request);
        Optional<String> commit = mergeRequestGitLabHookMsgConverter.toMsg(gitlabMsg);
        Assertions.assertTrue(commit.isPresent());
        Assertions.assertEquals(getFileString(result), commit.get());
    }

    private String getFileString(String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + filePath);
        return new String(Files.readAllBytes(file.toPath()));
    }

}