package chiloven.lukosbot.modules;

import chiloven.lukosbot.modules.github.GitHubDispatcher;
import chiloven.lukosbot.modules.help.HelpDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainDispatcher {
    private static final Logger logger = LogManager.getLogger(MainDispatcher.class);
    private final GitHubDispatcher githubDispatcher = new GitHubDispatcher();
    private final HelpDispatcher helpDispatcher = new HelpDispatcher();

    public String handle(String message) {
        if (message == null || message.trim().isEmpty()) {
            logger.info("收到空消息，返回帮助信息。");
            return helpDispatcher.handle("/help");
        }

        String text = message.trim();

        if (text.startsWith("/help")) {
            return helpDispatcher.handle(text);
        } else if (text.startsWith("/github")) {
            return githubDispatcher.handle(text);
        } else {
            return "未知命令：" + text + "\n请输入 /help 查看所有可用命令。";
        }
    }
}
