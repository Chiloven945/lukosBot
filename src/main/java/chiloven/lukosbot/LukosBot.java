package chiloven.lukosbot;

import chiloven.lukosbot.modules.MainDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LukosBot extends TelegramLongPollingBot {
    private static final Logger logger = LogManager.getLogger(LukosBot.class);
    private final MainDispatcher dispatcher = new MainDispatcher();
    private static String cachedToken = null;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/")) {
                String cleanText = messageText.replaceFirst("@[\\w_]+", "");

                String reply = dispatcher.handle(cleanText, chatId);

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));
                message.setText(reply);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    logger.warn("发送消息时出错: {}", e.getMessage());
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "lukos945_bot";  // 例如: MyTestBot
    }

    @Override
    public String getBotToken() {
        if (cachedToken != null) return cachedToken;
        try {
            // 读取项目根目录下的 bot.token 文件
            cachedToken = Files.readString(Paths.get("bot.token")).trim();
            return cachedToken;
        } catch (IOException e) {
            logger.error("无法读取 bot.token 文件: {}", e.getMessage());
            throw new RuntimeException("Bot Token 读取失败（未找到 bot.token 文件）");
        }
    }
}
