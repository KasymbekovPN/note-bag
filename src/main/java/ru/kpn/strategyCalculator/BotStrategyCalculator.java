package ru.kpn.strategyCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.i18n.builder.MessageBuilder;
import ru.kpn.i18n.builder.MessageBuilderFactory;

@Component
public class BotStrategyCalculator implements StrategyCalculator<BotApiMethod<?>> {

    private final MessageBuilderFactory messageBuilderFactory;

    @Autowired
    public BotStrategyCalculator(MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    @Override
    public synchronized BotApiMethod<?> calculate(String code, Object... args) {
        String chatId = String.valueOf(args[0]);
        MessageBuilder builder = messageBuilderFactory.create(code);
        for (int i = 1; i < args.length; i++) {
            builder.arg(args[i]);
        }
        return new SendMessage(chatId, builder.build());
    }
}
