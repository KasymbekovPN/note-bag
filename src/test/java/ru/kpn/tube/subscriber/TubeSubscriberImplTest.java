package ru.kpn.tube.subscriber;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.SubscriberStrategy;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TubeSubscriberImplTest {

    @Test
    void shouldCheckCalculateWithoutNullState() {
        TubeMessage message = TubeMessage.builder().nullState(true).build();
        TestStrategy strategy = new TestStrategy();
        TubeSubscriberImpl subscriber = new TubeSubscriberImpl(strategy);
        subscriber.calculate(message);
        assertThat(strategy.getNullState()).isTrue();
    }

    @Test
    void shouldCheckCalculateWithNullState() {
        TubeMessage message = TubeMessage.builder().nullState(false).build();
        TestStrategy strategy = new TestStrategy();
        TubeSubscriberImpl subscriber = new TubeSubscriberImpl(strategy);
        subscriber.calculate(message);
        assertThat(strategy.getNullState()).isFalse();
    }

    @Test
    void shouldCheckNextSetting() {
        StringBuffer sb = new StringBuffer();
        FillingTestStrategy strategy = new FillingTestStrategy(sb);
        TubeSubscriberImpl ts = new TubeSubscriberImpl(strategy);
        ts.setNext(new TubeSubscriberImpl(strategy));
        ts.setNext(new TubeSubscriberImpl(strategy));

        TubeMessage message = TubeMessage.builder().text("1").build();
        ts.calculate(message);

        String s = strategy.getSb().toString();
        assertThat(s).isEqualTo("111");
    }

    @Test
    void shouldCheckHookUpForNullRoot() {
        TubeSubscriber<TubeMessage, BotApiMethod<?>> root = null;
        TubeSubscriber<TubeMessage, BotApiMethod<?>> subscriber = new TubeSubscriberImpl(null);
        root = subscriber.hookUp(root);
        assertThat(root).isEqualTo(subscriber);
    }

    @Test
    void shouldCheckHookUpForNotNullRoot() {
        TubeSubscriber<TubeMessage, BotApiMethod<?>> root = new TubeSubscriberImpl(null);
        TubeSubscriber<TubeMessage, BotApiMethod<?>> subscriber = new TubeSubscriberImpl(null);
        TubeSubscriber<TubeMessage, BotApiMethod<?>> newRoot = subscriber.hookUp(root);
        assertThat(root).isEqualTo(newRoot);
    }

    @Getter
    private static class TestStrategy implements SubscriberStrategy<TubeMessage, BotApiMethod<?>>{

        private Boolean nullState;

        @Override
        public Optional<BotApiMethod<?>> execute(TubeMessage value) {
            nullState = value.getNullState();
            return Optional.empty();
        }
    }

    @Getter
    private static class FillingTestStrategy implements SubscriberStrategy<TubeMessage, BotApiMethod<?>>{

        private final StringBuffer sb;

        public FillingTestStrategy(StringBuffer sb) {
            this.sb = sb;
        }

        @Override
        public Optional<BotApiMethod<?>> execute(TubeMessage value) {
            sb.append(value.getText());
            return Optional.empty();
        }
    }
}
