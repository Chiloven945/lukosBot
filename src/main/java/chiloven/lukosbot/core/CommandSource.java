package chiloven.lukosbot.core;

public class CommandSource {
    private String reply;

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
}
