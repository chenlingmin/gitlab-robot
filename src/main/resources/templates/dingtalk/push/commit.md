**[(${gitlab.user_name})]**在 [(${gitlab.project.name})] 的 [(${#strings.replace(gitlab.ref, 'refs/heads/', '')})] 分支推送了以下代码

[# th:each="c: ${gitlab.commits}"]
* [ [(${#strings.substring(c.id, 0, 8)})] ]([(${c.url})]) [(${#strings.replace(c.message, '&#10', '')})]
[/]