package ru.kpn.strategy.calculaters.answerCalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.i18n.builder.MessageBuilder;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.seed.Seed;

import java.util.function.Function;

@Component
public class AnswerCalculator implements Function<Seed<String>, BotApiMethod<?>> {

    private final MessageBuilderFactory messageBuilderFactory;

    @Autowired
    public AnswerCalculator(MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    @Override
    public synchronized BotApiMethod<?> apply(Seed<String> rawMessageOld) {
        String chatId = String.valueOf(rawMessageOld.getArgs()[0]);
        MessageBuilder builder = messageBuilderFactory.create(rawMessageOld.getCode());
        for (int i = 0; i < rawMessageOld.getArgs().length; i++) {
            builder.arg(rawMessageOld.getArgs()[i]);
        }
        return new SendMessage(chatId, builder.build());
    }
}
