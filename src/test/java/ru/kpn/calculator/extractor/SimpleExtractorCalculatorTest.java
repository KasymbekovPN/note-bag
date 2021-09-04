package ru.kpn.calculator.extractor;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.SubscriberExtractor;
import ru.kpn.subscriber.Subscriber;
import utils.UpdateInstanceBuilder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleExtractorCalculatorTest {

    @Test
    void shouldCheckEmptyCalculation() {
        SimpleExtractorCalculator calculator = new SimpleExtractorCalculator(new TestSubscriberExecutor(10, new EmptyTestSubscriber()));
        assertThat(calculator.calculate(new UpdateInstanceBuilder().build())).isEmpty();
    }

    @Test
    void shouldCheckPresentCalculation() {
        SimpleExtractorCalculator calculator = new SimpleExtractorCalculator(new TestSubscriberExecutor(10, new PresentTestSubscriber()));
        assertThat(calculator.calculate(new UpdateInstanceBuilder().build())).isPresent();
    }

    private static class TestSubscriberExecutor implements SubscriberExtractor<Update, BotApiMethod<?>> {

        private final Subscriber<Update, BotApiMethod<?>> subscriber;

        private int downCounter;

        public TestSubscriberExecutor(int downCounter, Subscriber<Update, BotApiMethod<?>> subscriber) {
            this.downCounter = downCounter;
            this.subscriber = subscriber;
        }

        @Override
        public Optional<Subscriber<Update, BotApiMethod<?>>> getNext() {
            if (--downCounter > 0){
                return Optional.of(subscriber);
            }
            return Optional.empty();
        }
    }

    private static class EmptyTestSubscriber implements Subscriber<Update, BotApiMethod<?>> {
    }

    private static class PresentTestSubscriber implements Subscriber<Update, BotApiMethod<?>> {
        @Override
        public Optional<BotApiMethod<?>> executeStrategy(Update message) {
            return Optional.of(new SendMessage("1", "text"));
        }
    }
}
