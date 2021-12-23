package ru.kpn.strategy.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.rawMessage.RawMessageFactoryOld;
import ru.kpn.rawMessage.RawMessageOld;

import java.util.Optional;
import java.util.function.Function;

abstract public class BaseSubscriberStrategy implements Strategy<Update, BotApiMethod<?>> {

    private Function<RawMessageOld<String>, BotApiMethod<?>> answerCalculator;

    protected Function<Update, Boolean> matcher;
    protected Integer priority;
    private RawMessageFactoryOld<String> rawMessageFactoryOld;

    @Autowired
    public void setRawMessageFactory(RawMessageFactoryOld<String> rawMessageFactoryOld){
        this.rawMessageFactoryOld = rawMessageFactoryOld;
    }

    @Autowired
    public void setAnswerCalculator(Function<RawMessageOld<String>, BotApiMethod<?>> answerCalculator) {
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
        RawMessageOld<String> source = runAndGetRawMessage(value);
        return answerCalculator.apply(source);
    }

    protected String calculateChatId(Update value) {
        return value.getMessage().getChatId().toString();
    }

    protected RawMessageOld<String> createRawMessage(String code){
        return rawMessageFactoryOld.create(code);
    }
}
