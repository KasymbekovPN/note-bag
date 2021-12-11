package ru.kpn.strategyCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.i18n.builder.MessageBuilder;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.rawMessage.RawMessage;

// TODO: 11.12.2021 rename
@Component
public class BotStrategyCalculator implements StrategyCalculator<BotApiMethod<?>, String> {

    private final MessageBuilderFactory messageBuilderFactory;

    @Autowired
    public BotStrategyCalculator(MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    @Override
    public synchronized BotApiMethod<?> calculate(RawMessage<String> source) {
        String chatId = String.valueOf(source.getArgs()[0]);
        MessageBuilder builder = messageBuilderFactory.create(source.getCode());
        for (int i = 0; i < source.getArgs().length; i++) {
            builder.arg(source.getArgs()[i]);
        }
        return new SendMessage(chatId, builder.build());
    }
}
