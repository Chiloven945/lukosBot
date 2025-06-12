package chiloven.lukosbot;

import chiloven.lukosbot.Modules.MainDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class LukosBot extends TelegramLongPollingBot {
    private static final Logger logger = LogManager.getLogger(LukosBot.class);
    private final MainDispatcher dispatcher = new MainDispatcher();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.startsWith("/")) {
                String cleanText = messageText.replaceFirst("@[\\w_]+", "");

                String reply = dispatcher.handle(cleanText);

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
        return "8138224142:AAHhTIdZxm4CRjzZDSfDFhGGbIcuQlxO5Mw";  // 在BotFather那里拿到的Token
    }
}
