package chiloven.lukosbot.Modules.Help;

import chiloven.lukosbot.Core.CommandSource;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static chiloven.lukosbot.Modules.BrigadierUtils.argument;
import static chiloven.lukosbot.Modules.BrigadierUtils.literal;
import static com.mojang.brigadier.arguments.StringArgumentType.word;

public class HelpDispatcher {
    private static final Logger logger = LogManager.getLogger(HelpDispatcher.class);
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public HelpDispatcher() {
        dispatcher.register(
                literal("help")
                        .executes(ctx -> {
                            ctx.getSource().reply("""
                                    支持的命令列表：
                                    
                                    /help <command>   - 查看帮助
                                    /github           - GitHub 功能说明
                                    
                                    更多详细用法使用 `/help` 加命令（例：`/help github`）
                                    """);
                            logger.info("Default help command executed.");
                            return 1;
                        })
                        .then(literal("github")
                                .executes(ctx -> {
                                    ctx.getSource().reply("""
                                            GitHub 功能说明：
                                            
                                            /github user <用户名>        - 查询 GitHub 用户信息
                                            /github repo <owner>/<repo> - 查询 GitHub 仓库信息
                                            /github search <关键词>     - 搜索 GitHub 仓库
                                            """);
                                    logger.info("GitHub help command executed.");
                                    return 1;
                                })
                                .then(literal("user").executes(ctx -> {
                                    ctx.getSource().reply("/github user <用户名>   - 查询 GitHub 用户信息");
                                    logger.info("GitHub user help command executed.");
                                    return 1;
                                }))
                                .then(literal("repo").executes(ctx -> {
                                    ctx.getSource().reply("/github repo <owner>/<repo>   - 查询 GitHub 仓库信息");
                                    logger.info("GitHub repo help command executed.");
                                    return 1;
                                }))
                                .then(literal("search").executes(ctx -> {
                                    ctx.getSource().reply("/github search <关键词>   - 搜索 GitHub 仓库");
                                    return 1;
                                }))
                        )
                        // ... 其它分支 ...
                        .then(argument("topic", word()).executes(ctx -> {
                            ctx.getSource().reply("未知帮助主题 `" + ctx.getArgument("topic", String.class) + "`，输入 `/help` 查看全部命令。");
                            return 1;
                        }))
        );
    }

    /**
     * 未能正确匹配的命令处理方法。
     *
     * @param message 输入的命令消息
     * @return 处理结果字符串
     */
    public String handle(String message) {
        String input = message.trim().replaceFirst("^/", "");
        CommandSource src = new CommandSource();
        try {
            dispatcher.execute(new StringReader(input), src);
            return src.getReply();
        } catch (CommandSyntaxException e) {
            return "命令格式错误，输入 `/help` 查看帮助。";
        }
    }
}
