package ru.kpn.tube.strategy.regexp;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.BaseSubscriberStrategy;
import ru.kpn.tube.strategy.Matcher;

import java.util.Optional;

public class HelpSubscriberStrategy extends BaseSubscriberStrategy {

    // TODO: 14.08.2021 must be in some file
    private static final String TEXT =
            "/help - print this help message\r\n" +
            "/reset - bot reset";

    public HelpSubscriberStrategy(Matcher matcher) {
        super(matcher);
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(TubeMessage value) {
        return Optional.of(new SendMessage(value.getChatId(), TEXT));
    }
}
