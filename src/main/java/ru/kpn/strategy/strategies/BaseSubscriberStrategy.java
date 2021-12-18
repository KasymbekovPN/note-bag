package ru.kpn.strategy.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.rawMessage.RawMessageFactory;
import ru.kpn.rawMessage.RawMessage;

import java.util.Optional;
import java.util.function.Function;

abstract public class BaseSubscriberStrategy implements Strategy<Update, BotApiMethod<?>> {

    private Function<RawMessage<String>, BotApiMethod<?>> answerCalculator;

    protected Function<Update, Boolean> matcher;
    protected Integer priority;
    private RawMessageFactory<String> rawMessageFactory;

    @Autowired
    public void setRawMessageFactory(RawMessageFactory<String> rawMessageFactory){
        this.rawMessageFactory = rawMessageFactory;
    }

    @Autowired
    public void setAnswerCalculator(Function<RawMessage<String>, BotApiMethod<?>> answerCalculator) {
        this.answerCalculator = answerCalculator;
    }

    @Override
    public Integer getPriority() {
        return priority;
    }

    @Override
    public Optional<BotApiMethod<?>> execute(Update value) {
        return match(value) ? Optional.of(calculateBotApiMethod(value)) : Optional.empty();
    }

    private boolean match(Update value) {
        return matcher != null && matcher.apply(value);
    }

    private BotApiMethod<?> calculateBotApiMethod(Update value) {
        RawMessage<String> source = runAndGetRawMessage(value);
        return answerCalculator.apply(source);
    }

    protected String calculateChatId(Update value) {
        return value.getMessage().getChatId().toString();
    }

    protected RawMessage<String> createRawMessage(String code){
        return rawMessageFactory.create(code);
    }
}
