package com.chenlm.tools.robot.gitlab.dingtalk;

import com.chenlm.tools.robot.gitlab.dingtalk.at.ComposeGitLabHookAtWhoExtractor;
import com.chenlm.tools.robot.gitlab.dingtalk.msg.ComposeGitLabHookMsgConverter;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 钉钉推送API
 * <p>
 * 该接口临时提供，仅限spring boot版本尚未升级的内部服务调用
 * 后期待服务完成spring boot升级后（2.x+），不建议使用该接口。请使用kafka TOPIC: DingDingMsg
 *
 * @author zhanglinangyuan
 * @since 2022-08-29 18:00
 */
@RestController
@RequestMapping("/gitlab/")
@Slf4j
@RequiredArgsConstructor
public class GitLabController {
    final DingtalkMarkDownSender dingTalkSender;

    final ComposeGitLabHookMsgConverter gitLabHookMsgConverter;

    final ComposeGitLabHookAtWhoExtractor gitLabHookAtWhoExtractor;

    /**
     * 钉钉消息推送
     *
     * @param hookMsg
     */
    @PostMapping("/_hook")
    public void textPush(@RequestBody String hookMsg, @RequestParam String accessToken) {
        try {
            String projectName = JsonPath.read(hookMsg, "$.project.name");
            String object_kind = JsonPath.read(hookMsg, "$.object_kind");
            log.info("{} {}", projectName, object_kind);
        } catch (Exception e) {
            log.warn("记录日志出错", e);
        }
        log.debug("钉钉消息收到参数：{}", hookMsg);
        if (hookMsg == null) {
            return;
        }
        gitLabHookMsgConverter.toMsg(hookMsg).ifPresent(
                msg -> dingTalkSender.send(accessToken, msg, gitLabHookAtWhoExtractor.extract(hookMsg))
        );
    }

}
