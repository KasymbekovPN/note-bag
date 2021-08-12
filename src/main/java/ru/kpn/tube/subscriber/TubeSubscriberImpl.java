package ru.kpn.tube.subscriber;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.SubscriberStrategy;

import java.util.Optional;

public class TubeSubscriberImpl implements TubeSubscriber<TubeMessage, BotApiMethod<?>> {

    private final SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy;

    private TubeSubscriber<TubeMessage, BotApiMethod<?>> next;

    public TubeSubscriberImpl(SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy) {
        this.strategy = strategy;
    }

    @Override
    public Optional<BotApiMethod<?>> calculate(TubeMessage value) {
        Optional<BotApiMethod<?>> maybeExecResult = strategy.execute(value);
        if (maybeExecResult.isPresent()){
            return maybeExecResult;
        }
        if (next != null){
            return next.calculate(value);
        }
        // TODO: 12.08.2021 may be here need default answer
        return Optional.empty();
    }

    @Override
    public TubeSubscriber<TubeMessage, BotApiMethod<?>> hookUp(TubeSubscriber<TubeMessage, BotApiMethod<?>> previous) {
        if (previous == null) {
            return this;
        }

        previous.setNext(this);
        return previous;
    }

    @Override
    public void setNext(TubeSubscriber<TubeMessage, BotApiMethod<?>> next) {
        if (this.next == null){
            this.next = next;
        } else {
            this.next.setNext(next);
        }
    }
}
