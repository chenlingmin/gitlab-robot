package com.chenlm.tools.robot.gitlab.dingtalk.at;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MergeRequestGitLabHookAtWhoExtractorTest {

    @Autowired
    private MergeRequestGitLabHookAtWhoExtractor mergeRequestGitLabHookAtWhoExtractor;

    @Test
    void extract() throws IOException {
        testExtract("gitlab/merge_request/open.json", Arrays.asList("18888888888"));
        testExtract("gitlab/merge_request/close.json", Arrays.asList("17777777777"));
        testExtract("gitlab/merge_request/update.json", Arrays.asList("17777777777", "18888888888"));
        testExtract("gitlab/merge_request/merge.json", Arrays.asList("17777777777"));
    }


    void testExtract(String request, List<String> result) throws IOException {
        String gitlabMsg = getFileString(request);
        List<String> atList = mergeRequestGitLabHookAtWhoExtractor.extract(gitlabMsg);
        Assertions.assertEquals(result, atList);
    }


    private String getFileString(String filePath) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + filePath);
        return new String(Files.readAllBytes(file.toPath()));
    }
}