package chiloven.lukosbot.Modules.GitHub;

import com.google.gson.JsonObject;

public class Repo {
    public String full_name;
    public String description;
    public String html_url;
    public String language;
    public int stargazers_count;
    public int forks_count;

    public static Repo fromJson(JsonObject json) {
        return Api.gson.fromJson(json, Repo.class);
    }

    /**
     * 将仓库信息转换为字符串格式，便于输出
     *
     * @return 格式化的仓库信息字符串
     */
    @Override
    public String toString() {
        return String.format(
                """
                        仓库: %s
                        主页: %s
                        语言: %s | Star: %d | Fork: %d
                        描述: %s
                        """,
                full_name, html_url, language, stargazers_count, forks_count, description == null ? "无" : description
        );
    }
}
