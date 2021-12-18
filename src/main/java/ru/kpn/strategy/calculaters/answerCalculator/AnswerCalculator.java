package ru.kpn.strategy.calculaters.answerCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.i18n.builder.MessageBuilder;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

@Component
public class AnswerCalculator implements Function<RawMessage<String>, BotApiMethod<?>> {

    private final MessageBuilderFactory messageBuilderFactory;

    @Autowired
    public AnswerCalculator(MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    @Override
    public synchronized BotApiMethod<?> apply(RawMessage<String> rawMessage) {
        String chatId = String.valueOf(rawMessage.getArgs()[0]);
        MessageBuilder builder = messageBuilderFactory.create(rawMessage.getCode());
        for (int i = 0; i < rawMessage.getArgs().length; i++) {
            builder.arg(rawMessage.getArgs()[i]);
        }
        return new SendMessage(chatId, builder.build());
    }
}
