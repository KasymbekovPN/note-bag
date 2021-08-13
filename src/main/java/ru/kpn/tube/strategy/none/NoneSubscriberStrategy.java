package ru.kpn.tube.strategy.none;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.BaseSubscriberStrategy;
import ru.kpn.tube.strategy.Matcher;

import java.util.Optional;

public class NoneSubscriberStrategy extends BaseSubscriberStrategy {

    public NoneSubscriberStrategy() {
        super(new NoneSubscriberStrategyMatcher());
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(TubeMessage value) {
        return Optional.of(new SendMessage(value.getChatId(), calculateMessage(value)));
    }

    private String calculateMessage(TubeMessage value) {
        // TODO: 12.08.2021 translation
        return String.format("There is unknown input : %s", value.getText());
    }

    private static class NoneSubscriberStrategyMatcher implements Matcher{
        @Override
        public Boolean match(String value) {
            return true;
        }
    }
}
