package chiloven.lukosbot.Modules.GitHub;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Api {
    static final Gson gson = new Gson();
    private static final String BASE_URL = "https://api.github.com";

    /**
     * 获取 GitHub 用户信息
     *
     * @param username GitHub 用户名
     * @return JsonObject 包含用户信息
     * @throws IOException 如果请求失败或网络错误
     */
    public static JsonObject getUser(String username) throws IOException {
        return getJson(BASE_URL + "/users/" + username);
    }

    /**
     * 获取 GitHub 仓库信息
     *
     * @param owner GitHub 用户名或组织名
     * @param repo  GitHub 仓库名
     * @return JsonObject 包含仓库信息
     * @throws IOException 如果请求失败或网络错误
     */
    public static JsonObject getRepo(String owner, String repo) throws IOException {
        return getJson(BASE_URL + "/repos/" + owner + "/" + repo);
    }

    /**
     * 搜索 GitHub 仓库
     *
     * @param keyword 搜索关键词
     * @return JsonObject 包含搜索结果
     * @throws IOException 如果请求失败或网络错误
     */
    public static JsonObject searchRepo(String keyword) throws IOException {
        return getJson(BASE_URL + "/search/repositories?q=" + keyword);
    }

    /**
     * 通用方法，用于发送 GET 请求并解析 JSON 响应
     *
     * @param urlString 完整的 URL 字符串
     * @return JsonObject 包含响应内容
     * @throws IOException 如果请求失败或网络错误
     */
    private static JsonObject getJson(String urlString) throws IOException {
        URL url = URI.create(urlString).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();

        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream())) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }
}
