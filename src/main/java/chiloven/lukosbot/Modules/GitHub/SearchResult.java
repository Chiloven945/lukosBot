package chiloven.lukosbot.Modules.GitHub;

import com.google.gson.JsonObject;

import java.util.List;

public class SearchResult {
    public List<Repo> items;

    public static SearchResult fromJson(JsonObject json) {
        return Api.gson.fromJson(json, SearchResult.class);
    }

    /**
     * 将搜索结果转换为简要文本格式，便于快速查看
     *
     * @param topN 要显示的前 N 个仓库
     * @return 格式化的简要文本
     */
    public String briefTop(int topN) {
        if (items == null || items.isEmpty()) return "未搜索到任何仓库。";
        StringBuilder sb = new StringBuilder("【仓库搜索结果】\n");
        int count = Math.min(items.size(), topN);
        for (int i = 0; i < count; i++) {
            Repo repo = items.get(i);
            sb.append(repo.full_name)
                    .append(" - ").append(repo.stargazers_count).append("★\n")
                    .append(repo.html_url).append("\n\n");
        }
        return sb.toString();
    }
}
