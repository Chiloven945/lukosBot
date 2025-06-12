package chiloven.lukosbot.Modules.GitHub;

import chiloven.lukosbot.Core.CommandSource;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static chiloven.lukosbot.Modules.BrigadierUtils.argument;
import static chiloven.lukosbot.Modules.BrigadierUtils.literal;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;

public class GitHubDispatcher {
    private static final Logger logger = LogManager.getLogger(GitHubDispatcher.class);
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public GitHubDispatcher() {
        dispatcher.register(
                literal("github")
                        .then(literal("user")
                                .then(argument("username", greedyString())
                                        .executes(ctx -> {
                                            String username = ctx.getArgument("username", String.class);
                                            ctx.getSource().reply(handleUser(username));
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("repo")
                                .then(argument("repo", greedyString())
                                        .executes(ctx -> {
                                            String repoArg = ctx.getArgument("repo", String.class);
                                            ctx.getSource().reply(handleRepo(repoArg));
                                            return 1;
                                        })
                                )
                        )
                        .then(literal("search")
                                .then(argument("keyword", greedyString())
                                        .executes(ctx -> {
                                            String keyword = ctx.getArgument("keyword", String.class);
                                            ctx.getSource().reply(handleSearch(keyword));
                                            return 1;
                                        })
                                )
                        )
                        .executes(ctx -> {
                            ctx.getSource().reply(usage());
                            return 1;
                        })
        );
    }

    private static String usage() {
        return "用法：\n"
                + "/github user <用户名>\n"
                + "/github repo <owner>/<repo>\n"
                + "/github search <关键词>";
    }

    public String handle(String message) {
        logger.info("处理 GitHub 命令: {}", message);
        String input = message.trim().replaceFirst("^/", "");
        CommandSource src = new CommandSource();
        try {
            dispatcher.execute(new StringReader(input), src);
            return src.getReply();
        } catch (CommandSyntaxException e) {
            logger.warn("命令格式错误: {}", message, e);
            return "GitHub命令格式错误，输入 /github 查看用法";
        }
    }

    // **只做参数分发，解析、组装全部交给数据类**
    private String handleUser(String username) {
        try {
            JsonObject userObj = Api.getUser(username);
            if (userObj.has("message")) {
                logger.warn("查询 GitHub 用户失败: {}", userObj.get("message").getAsString());
                return "找不到用户：" + username;
            }
            return User.fromJson(userObj).toString();
        } catch (Exception e) {
            logger.error("查询 GitHub 用户出错", e);
            return "查询GitHub用户时出错：" + e.getMessage();
        }
    }

    private String handleRepo(String repoArg) {
        try {
            String[] repoParts = repoArg.split("/", 2);
            if (repoParts.length != 2) {
                logger.warn("仓库格式错误: {}", repoArg);
                return "仓库格式应为 owner/repo";
            }
            JsonObject repoObj = Api.getRepo(repoParts[0], repoParts[1]);
            if (repoObj.has("message")) {
                logger.warn("查询 GitHub 仓库失败: {}", repoObj.get("message").getAsString());
                return "找不到仓库：" + repoArg;
            }
            return Repo.fromJson(repoObj).toString();
        } catch (Exception e) {
            logger.error("查询 GitHub 仓库出错", e);
            return "查询GitHub仓库时出错：" + e.getMessage();
        }
    }

    private String handleSearch(String keyword) {
        try {
            JsonObject resultObj = Api.searchRepo(keyword);
            SearchResult result = SearchResult.fromJson(resultObj);
            return result.briefTop(3);
        } catch (Exception e) {
            logger.error("GitHub 搜索出错", e);
            return "搜索GitHub仓库时出错：" + e.getMessage();
        }
    }
}
