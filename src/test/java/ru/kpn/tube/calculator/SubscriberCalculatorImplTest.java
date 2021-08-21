package ru.kpn.tube.calculator;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.extractor.TubeSubscriberExtractor;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberCalculatorImplTest {

    @Test
    void shouldCheckEmptyCalculation() {
        TubeMessage tm = TubeMessage.builder().build();
        SubscriberCalculatorImpl calculator = new SubscriberCalculatorImpl(new TestTubeSubscriberExecutor(10, new EmptyTestTubeSubscriber()));
        assertThat(calculator.calculate(tm)).isEmpty();
    }

    @Test
    void shouldCheckPresentCalculation() {
        TubeMessage tm = TubeMessage.builder().build();
        SubscriberCalculatorImpl calculator = new SubscriberCalculatorImpl(new TestTubeSubscriberExecutor(10, new PresentTestTubeSubscriber()));
        assertThat(calculator.calculate(tm)).isPresent();
    }

    private static class TestTubeSubscriberExecutor implements TubeSubscriberExtractor<TubeMessage, BotApiMethod<?>> {

        private final TubeSubscriber<TubeMessage, BotApiMethod<?>> subscriber;

        private int downCounter;

        public TestTubeSubscriberExecutor(int downCounter, TubeSubscriber<TubeMessage, BotApiMethod<?>> subscriber) {
            this.downCounter = downCounter;
            this.subscriber = subscriber;
        }

        @Override
        public Optional<TubeSubscriber<TubeMessage, BotApiMethod<?>>> getNext() {
            if (--downCounter > 0){
                return Optional.of(subscriber);
            }
            return Optional.empty();
        }
    }

    private static class EmptyTestTubeSubscriber implements TubeSubscriber<TubeMessage, BotApiMethod<?>>{
    }

    private static class PresentTestTubeSubscriber implements TubeSubscriber<TubeMessage, BotApiMethod<?>>{
        @Override
        public Optional<BotApiMethod<?>> executeStrategy(TubeMessage message) {
            return Optional.of(new SendMessage("1", "text"));
        }
    }
}
