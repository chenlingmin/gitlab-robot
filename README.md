# gitlab 智能机器人

问： 为什么要开发这个？钉钉不是已经提供了 Gitlab 机器人吗？

答：因为钉钉自带的机器人，互动性不强，例如我在一段代码上评论了，但被评论的人，你不主动通知他，他就不知道。在这个场景中，就需要一个智能机器人 @他。

## 功能简介

目前支持 gitlab 的以下几种消息

* push evens  
* comments
  * 支持评论区 @ 同步钉钉 @
  * 评论 issue 默认 @issue 提出人
  * 评论 merge request 默认 @merge request 提出人
* issues events
  * @ issues 负责人
  * reopen close 等都会 @ 相关人
* merge request
  * open close reopen merge update 都会 @ 相关人
  
## 如何使用

* 配置好 `application.yaml` 中的 `gitlab.url`、`gitlab.access-token` ，方便同步钉钉用户

* 在 [application.yaml](./src/main/resources/application.yaml) 中配置 `gitlab.gitlab-to-dingtalk-users` 项，内容为 gitlab 与 钉钉手机号的映射。

* 在钉钉群中添加自定义机器人

![](./.img/add_robot.png)

* 配置钉钉机器人，关键字固定配置 hello

![](./.img/config_robot.png)

* 复制出 `access_token`, 贴在 url 后面 `http://domain.name/gitlab/_hook?accessToken=`

![](./.img/access_token.png)

* 在 gitlab 相应的 repository 中，依次 Setting -> Integrations, 在 URL 中填入上步的 URL, 勾选 push events、 comments、issues events、merge request envets.

![](./.img/config_gitlab.png)


## 预览功能

![](./.img/snapshot.png)

## 快速了解

**依赖技术**

* [JsonPath](https://github.com/json-path/JsonPath) json 解析
* [Thymeleaf](https://www.thymeleaf.org/) 模板引擎


**系统上下文**

![](https://www.plantuml.com/plantuml/svg/XPFFYXD14CRl_HJjd38WEvSzYIohMT632-8UF8REJ3apfASxTjV43I9aGK4F4lKWA8AYwv-3uir3LdsQP2Rx5dQdpJ1cCvkGGoLLtw-_hgeckngPmZpZv48gGfv7G2-tjuFsTgSj1R9KWDfPayhwKe307K9kWT9Ij28O09TzK1UfD_ircerVp4UFFP-GWw56o5fnYfnrJVBw8l1Cb23sBmM1oIHvTojBuvmXXCbMA3FFN-gYLCJ8UC_KdxvyQhvU5PL_ZikJHwQqSjgvAdCHCJNyTrn7oQv48wZ_z7pwW53pgMxH2Wr0ISv1EOCtMRTT_MKFr7rbVD1i47h-YdPfS9-ftfwyBmv0H0lEtjg4fIqbL4UzCxpGHXgSYnjIzlPrRCmCGE42UsxUqZ4PPi21tTsCle9t4ddj41KBKLhuXWgBLn33eK4_9EGsyFhSgn4Nu-VJoV7qv6Fv_YX8jSuXo435G1NSoq4ZBRylngXrOgSVs4NmVCTRGluRoVpBPxDGD366cdJr-aqjdF_-LZpxKFnyKXpzMErOCwkXP_Qsi_6hqz6RyjsdwMHIxz5PQutRulHkiOktazdhhuwriYoEs42fwCqyz-gq0UqQZNtsVm40)

**交互时序**

![](https://www.plantuml.com/plantuml/svg/uof8B2h9JCuiICmhKG03zyoIdCGa23knM24TpELApiyhAShFGLBX3179IIs2AqKJv-9A1j799PdvUSKWNJw9IHxv-Tc-nUb0HMMfHIL0lOcuP71eTYm9pyXthIWfIamkoKTenvG3MfyEyOgk7ORJh1IURUjurhYU2wWoioon91NJyUW2G6DmkDHAuSNUDSzw5q6DnAu8kojUx9_oTFOyYCQdRQrFr-t4RI-c5q7Uw9_mj7-QWeF2arsxk70emsitHElJZkal5gx0QdTmQbuAIFdJ_eal6ngXVWG9GgHJClFIIn9pij4Km8GnJNY-U_ApUNEW9c2w34CXkhIk7KG4JLbAAkwev080)
  
