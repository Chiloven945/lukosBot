package chiloven.lukosbot.modules.luck;

import chiloven.lukosbot.core.CommandSource;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static chiloven.lukosbot.modules.BrigadierUtils.literal;

public class LuckDispatcher {
    private static final Logger logger = LogManager.getLogger(LuckDispatcher.class);
    // chatId -> (date, luckNum)
    private static final Map<Long, LuckInfo> luckMap = new ConcurrentHashMap<>();
    private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

    public LuckDispatcher() {
        dispatcher.register(
                literal("luck")
                        .executes(ctx -> {
                            // 获取聊天ID
                            Long chatId = ctx.getSource().getChatId();
                            int luck = getLuckForToday(chatId);
                            ctx.getSource().reply("🎲 你今天的人品是：" + luck);
                            logger.info("群/私聊 {} 使用 /luck，数字={}", chatId, luck);
                            return 1;
                        })
        );
    }

    /**
     * 处理 /luck 命令
     * @param message 命令消息内容
     * @param chatId 聊天ID
     * @return 处理结果
     */
    public String handle(String message, Long chatId) {
        CommandSource src = new CommandSource();
        src.setChatId(chatId);
        try {
            dispatcher.execute(new StringReader(message.trim().replaceFirst("^/", "")), src);
            return src.getReply();
        } catch (CommandSyntaxException e) {
            return "命令格式错误，输入 /luck";
        }
    }

    private int getLuckForToday(Long chatId) {
        LocalDate today = LocalDate.now();
        LuckInfo luckInfo = luckMap.get(chatId);
        if (luckInfo != null && luckInfo.date().equals(today)) {
            return luckInfo.luck();
        }
        int newLuck = ThreadLocalRandom.current().nextInt(1, 101);
        luckMap.put(chatId, new LuckInfo(today, newLuck));
        return newLuck;
    }

    private record LuckInfo(LocalDate date, int luck) {
    }
}
