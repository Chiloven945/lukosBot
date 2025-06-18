package chiloven.lukosbot.core;

public class CommandSource {
    private String reply;
    private Long chatId;

    /**
     * 回复消息
     *
     * @param msg 要回复的消息内容
     */
    public void reply(String msg) {
        this.reply = msg;
    }

    /**
     * 获取回复消息
     *
     * @return 回复的消息内容
     */
    public String getReply() {
        return reply;
    }

    /**
     * 获取聊天ID
     *
     * @return 聊天ID
     */
    public Long getChatId() {
        return chatId;
    }

    /**
     * 设置聊天ID
     *
     * @param chatId 聊天ID
     */
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
