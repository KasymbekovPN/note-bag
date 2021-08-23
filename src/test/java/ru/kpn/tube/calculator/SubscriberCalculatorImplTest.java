package ru.kpn.tube.calculator;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.extractor.TubeSubscriberExtractor;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberCalculatorImplTest {

    @Test
    void shouldCheckEmptyCalculation() {
        SubscriberCalculatorImpl calculator = new SubscriberCalculatorImpl(new TestTubeSubscriberExecutor(10, new EmptyTestTubeSubscriber()));
        assertThat(calculator.calculate(new Update())).isEmpty();
    }

    @Test
    void shouldCheckPresentCalculation() {
        SubscriberCalculatorImpl calculator = new SubscriberCalculatorImpl(new TestTubeSubscriberExecutor(10, new PresentTestTubeSubscriber()));
        assertThat(calculator.calculate(new Update())).isPresent();
    }

    private static class TestTubeSubscriberExecutor implements TubeSubscriberExtractor<Update, BotApiMethod<?>> {

        private final TubeSubscriber<Update, BotApiMethod<?>> subscriber;

        private int downCounter;

        public TestTubeSubscriberExecutor(int downCounter, TubeSubscriber<Update, BotApiMethod<?>> subscriber) {
            this.downCounter = downCounter;
            this.subscriber = subscriber;
        }

        @Override
        public Optional<TubeSubscriber<Update, BotApiMethod<?>>> getNext() {
            if (--downCounter > 0){
                return Optional.of(subscriber);
            }
            return Optional.empty();
        }
    }

    private static class EmptyTestTubeSubscriber implements TubeSubscriber<Update, BotApiMethod<?>>{
    }

    private static class PresentTestTubeSubscriber implements TubeSubscriber<Update, BotApiMethod<?>>{
        @Override
        public Optional<BotApiMethod<?>> executeStrategy(Update message) {
            return Optional.of(new SendMessage("1", "text"));
        }
    }
}
