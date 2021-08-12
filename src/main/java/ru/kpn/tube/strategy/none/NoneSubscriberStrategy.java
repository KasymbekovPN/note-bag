package ru.kpn.tube.strategy.none;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.SubscriberStrategy;

import java.util.Optional;

public class NoneSubscriberStrategy implements SubscriberStrategy<TubeMessage, BotApiMethod<?>> {

    @Override
    public Optional<BotApiMethod<?>> execute(TubeMessage value) {
        return Optional.of(new SendMessage(value.getChatId().toString(), calculateMessage(value)));
    }

    private String calculateMessage(TubeMessage value) {
        // TODO: 12.08.2021 translation
        return String.format("There is unknown input : %s", value.getText());
    }
}
