package chiloven.lukosbot.modules;

import chiloven.lukosbot.core.CommandSource;
import chiloven.lukosbot.modules.github.GitHubDispatcher;
import chiloven.lukosbot.modules.help.HelpDispatcher;
import chiloven.lukosbot.modules.luck.LuckDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainDispatcher {
    private static final Logger logger = LogManager.getLogger(MainDispatcher.class);
    private final GitHubDispatcher gitHubDispatcher = new GitHubDispatcher();
    private final HelpDispatcher helpDispatcher = new HelpDispatcher();
    private final LuckDispatcher luckDispatcher = new LuckDispatcher();
    private final CommandSource commandSource = new CommandSource();

    public String handle(String message, long chatId) {
        if (message == null || message.trim().isEmpty()) {
            logger.info("收到空消息，返回帮助信息。");
            return helpDispatcher.handle("/help");
        }

        String text = message.trim();

        if (text.startsWith("/help")) {
            return helpDispatcher.handle(text);
        } else if (text.startsWith("/github")) {
            return gitHubDispatcher.handle(text);
        } else if (text.startsWith("/luck")) {
            return luckDispatcher.handle(text, chatId);
        } else {
            return "未知命令：" + text + "\n请输入 /help 查看所有可用命令。";
        }
    }
}
