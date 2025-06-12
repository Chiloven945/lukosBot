package chiloven.lukosbot.Modules.GitHub;

import com.google.gson.JsonObject;

public class User {
    public String login;
    public String name;
    public String html_url;
    public int followers;
    public int following;
    public int public_repos;
    public String avatar_url;

    public static User fromJson(JsonObject json) {
        return Api.gson.fromJson(json, User.class);
    }

    /**
     * 将用户信息转换为字符串格式，便于输出
     *
     * @return 格式化的用户信息字符串
     */
    @Override
    public String toString() {
        return String.format(
                """
                        用户: %s (%s)
                        主页: %s
                        公开仓库: %d | 粉丝: %d | 关注: %d
                        """,
                name != null ? name : login, login, html_url, public_repos, followers, following
        );
    }
}
