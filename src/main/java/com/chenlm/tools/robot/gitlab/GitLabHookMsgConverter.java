package com.chenlm.tools.robot.gitlab;


import java.util.Optional;

/**
 * @author zhangliangyuan
 * @description 提取消息
 * @date 2022/8/30 11:30
 **/
public interface GitLabHookMsgConverter {
    /**
     * @author zhangliangyuan
     * @Description 消息内容
     * @param gitLabHook
     * @create 2022/8/30 10:32
     * @return
     */
    Optional<String> toMsg(String hookMsg);
}
