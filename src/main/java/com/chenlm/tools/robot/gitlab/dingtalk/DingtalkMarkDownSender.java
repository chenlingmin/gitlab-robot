package com.chenlm.tools.robot.gitlab.dingtalk;

import com.chenlm.tools.robot.gitlab.Sender;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class DingtalkMarkDownSender implements Sender {
    public static String AcceptTo = "https://oapi.dingtalk.com/robot/send?access_token=";


    @Override
    public void send(String channel, String msg, List<String> atList) {
        DingTalkClient client = loadClient(channel);
        OapiRobotSendRequest request = getOapiRobotSendRequest(msg, atList);
        try {
            OapiRobotSendResponse response = client.execute(request);
            if (!response.isSuccess()) {
                log.warn("钉钉消息发送失败：{}", response.getErrmsg());
            }
        } catch (ApiException e) {
            log.warn("钉钉消息发送失败：", e);
        }
    }

    private OapiRobotSendRequest getOapiRobotSendRequest(String msg, List<String> atList) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        // 消息类型，固定为：text
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdownRequest = new OapiRobotSendRequest.Markdown();
        markdownRequest.setTitle("来自 Gitlab 消息");
        markdownRequest.setText(enhanceMsg(msg, atList));
        request.setMarkdown(markdownRequest);
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        // 被@人的手机号（在content里添加@人的手机号）
        at.setAtMobiles(atList);
        // 是否@所有人
        at.setIsAtAll(false);
        request.setAt(at);
        return request;
    }

    private DingTalkClient loadClient(String accessToken) {
        return new DefaultDingTalkClient(AcceptTo + accessToken);
    }

    private String enhanceMsg(String msg, List<String> asList) {
        Optional<String> atString = asList.stream()
                .map(a -> " @" + a)
                .reduce((s, s2) -> s + s2);
        return "<!--hello-->\n" + msg + atString.orElse("");
    }

}
