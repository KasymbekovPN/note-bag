package ru.kpn.tube.strategy;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.model.telegram.TubeMessage;

import java.util.Optional;

abstract public class BaseSubscriberStrategy implements SubscriberStrategy<TubeMessage, BotApiMethod<?>> {

    protected Matcher matcher;

    public BaseSubscriberStrategy(){}

    public BaseSubscriberStrategy(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Optional<BotApiMethod<?>> execute(TubeMessage value) {
        if (check(value)){
            return executeImpl(value);
        }

        return Optional.empty();
    }

    private boolean check(TubeMessage value) {
        return matchTemplate(value);
    }

    private boolean matchTemplate(TubeMessage value) {
        return !value.getNullState() && matcher != null && matcher.match(value.getText());
    }

    protected abstract Optional<BotApiMethod<?>> executeImpl(TubeMessage value);
}
