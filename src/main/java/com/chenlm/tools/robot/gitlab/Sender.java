package com.chenlm.tools.robot.gitlab;


import java.util.List;

/**
 * @author zhangliangyuan
 * @description 钉钉发送接口
 * @date 2022/8/30 11:30
 **/
public interface Sender {
    void send(String channel, String msg, List<String> atList);

}
