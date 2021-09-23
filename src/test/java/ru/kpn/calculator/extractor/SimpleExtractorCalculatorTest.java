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
    void shouldCheckDefaultCalculation() {
        SimpleExtractorCalculator calculator = new SimpleExtractorCalculator(
                        new TestSubscriberExecutor(10,new EmptyTestSubscriber()),
                        new TestDefaultAnswerGenerator());
        SendMessage result = (SendMessage) calculator.calculate(new UpdateInstanceBuilder().build());
        assertThat(result.getChatId()).isEqualTo(TestDefaultAnswerGenerator.CHAT_ID);
        assertThat(result.getText()).isEqualTo(TestDefaultAnswerGenerator.TEXT);
    }

    @Test
    void shouldCheckPresentCalculation() {
        SimpleExtractorCalculator calculator = new SimpleExtractorCalculator(
                new TestSubscriberExecutor(10, new PresentTestSubscriber()),
                new TestDefaultAnswerGenerator());
        SendMessage result = (SendMessage) calculator.calculate(new UpdateInstanceBuilder().build());
        assertThat(result.getChatId()).isEqualTo(PresentTestSubscriber.CHAT_ID);
        assertThat(result.getText()).isEqualTo(PresentTestSubscriber.TEXT);
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
        @Override
        public int compareTo(Integer integer) {
            return 0;
        }
    }

    private static class PresentTestSubscriber implements Subscriber<Update, BotApiMethod<?>> {
        private static final String CHAT_ID = "1";
        private static final String TEXT = "some text";

        @Override
        public int compareTo(Integer integer) {
            return 0;
        }

        @Override
        public Optional<BotApiMethod<?>> executeStrategy(Update message) {
            return Optional.of(new SendMessage(CHAT_ID, TEXT));
        }
    }


    private static class TestDefaultAnswerGenerator implements SimpleExtractorCalculator.AnswerGenerator {
        private static final String CHAT_ID = "2";
        private static final String TEXT = "default text";

        @Override
        public BotApiMethod<?> generate(Update update) {
            return new SendMessage(CHAT_ID, TEXT);
        }
    }
}
