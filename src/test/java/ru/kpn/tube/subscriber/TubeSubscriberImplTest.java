package ru.kpn.tube.subscriber;

import lombok.Getter;
import org.junit.jupiter.api.Test;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.strategy.SubscriberStrategy;

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
        TubeSubscriber<TubeMessage> root = null;
        TubeSubscriber<TubeMessage> subscriber = new TubeSubscriberImpl(null);
        root = subscriber.hookUp(root);
        assertThat(root).isEqualTo(subscriber);
    }

    @Test
    void shouldCheckHookUpForNotNullRoot() {
        TubeSubscriber<TubeMessage> root = new TubeSubscriberImpl(null);
        TubeSubscriber<TubeMessage> subscriber = new TubeSubscriberImpl(null);
        TubeSubscriber<TubeMessage> newRoot = subscriber.hookUp(root);
        assertThat(root).isEqualTo(newRoot);
    }

    @Getter
    private static class TestStrategy implements SubscriberStrategy<TubeMessage>{

        private Boolean nullState;

        @Override
        public boolean execute(TubeMessage value) {
            nullState = value.getNullState();
            return false;
        }
    }

    @Getter
    private static class FillingTestStrategy implements SubscriberStrategy<TubeMessage>{

        private StringBuffer sb;

        public FillingTestStrategy(StringBuffer sb) {
            this.sb = sb;
        }

        @Override
        public boolean execute(TubeMessage value) {
            sb.append(value.getText());
            return false;
        }
    }
}
