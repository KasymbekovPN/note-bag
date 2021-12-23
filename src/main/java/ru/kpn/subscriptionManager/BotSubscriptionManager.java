package ru.kpn.subscriptionManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.bot.transmitter.Transmitter;
import ru.kpn.rawMessage.RawMessageOld;
import ru.kpn.subscriber.Subscriber;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BotSubscriptionManager implements SubscriptionManager<Update, BotApiMethod<?>> {

    private final Transmitter<BotApiMethod<?>> transmitter;
    private final Function<RawMessageOld<String>, BotApiMethod<?>> defaultAnswerCalculator;
    private final Set<Subscriber<Update, BotApiMethod<?>>> subscribers = new TreeSet<>();

    @Override
    public void subscribe(Subscriber<Update, BotApiMethod<?>> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public synchronized void execute(Update update) {
        Optional<BotApiMethod<?>> maybeResult = Optional.empty();;
        for (Subscriber<Update, BotApiMethod<?>> subscriber : subscribers) {
            maybeResult = subscriber.executeStrategy(update);
            if (maybeResult.isPresent()){
                break;
            }
        }
        BotApiMethod<?> result = maybeResult.isPresent() ? maybeResult.get() : calculateDefaultAnswer(update);
        transmitter.transmit(result);
    }

    private BotApiMethod<?> calculateDefaultAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        RawMessageOld<String> rawMessageOld = new RawMessageOld<>() {
            @Override
            public String getCode() {
                return "noneSubscriberStrategy.unknownInput";
            }

            @Override
            public RawMessageOld<String> add(Object o) {return this;}

            @Override
            public Object[] getArgs() {
                return new Object[]{chatId, text};
            }

            @Override
            public RawMessageOld<String> setCode(String code) {
                return null;
            }
        };

        return defaultAnswerCalculator.apply(rawMessageOld);
    }
}
