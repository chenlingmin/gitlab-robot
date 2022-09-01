package com.chenlm.tools.robot.gitlab;


import java.util.List;

/**
 * @author zhangliangyuan
 * @description 提取钉钉@人员集合
 * @date 2022/8/30 11:30
 **/
public interface GitLabHookAtWhoExtractor {
    /**
     * 提取@人员集合
     * @param gitLabHook
     * @return
     */
    List<String> extract(String gitLabHook);
}
