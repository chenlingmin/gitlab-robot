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
class NoteGitLabHookMsgConverterTest {

    @Autowired
    private NoteGitLabHookMsgConverter noteGitLabHookMsgConverter;

    @Test
    void toMsg() throws IOException {

        testNoteMsg("gitlab/note/merge_request.json", "templates/dingtalk/result/note/note_merge_request.md");
        testNoteMsg("gitlab/note/issue.json", "templates/dingtalk/result/note/note_issue.md");
        testNoteMsg("gitlab/note/commit.json", "templates/dingtalk/result/note/note_commit.md");
    }

    //
    void testNoteMsg(String request, String result) throws IOException {
        String gitlabMsg = getFileString(request);
        Optional<String> commit = noteGitLabHookMsgConverter.toMsg(gitlabMsg);
        Assertions.assertTrue(commit.isPresent());
        Assertions.assertEquals(getFileString(result), commit.get());
    }


    private String getFileString(String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + filePath);
        return new String(Files.readAllBytes(file.toPath()));
    }
}