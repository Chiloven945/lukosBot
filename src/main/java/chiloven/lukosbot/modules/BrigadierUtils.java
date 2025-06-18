package chiloven.lukosbot.modules;

import chiloven.lukosbot.core.CommandSource;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public class BrigadierUtils {
    /**
     * 创建一个字面量命令构建器
     *
     * @param name 命令名称
     * @return 字面量命令构建器
     */
    public static LiteralArgumentBuilder<CommandSource> literal(String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    /**
     * 创建一个必需参数的命令构建器
     *
     * @param name 参数名称
     * @param type 参数类型
     * @param <T>  参数类型
     * @return 必需参数的命令构建器
     */
    public static <T> RequiredArgumentBuilder<CommandSource, T> argument(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }
}
