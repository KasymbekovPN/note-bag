package ru.kpn.tube.extractor;

import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TubeSubscriberExtractorImplTest {

    private static final int AMOUNT = 10;

    @Test
    void shouldCheckGetNextMethod() {
        TubeSubscriberExtractorImpl extractor = new TubeSubscriberExtractorImpl(new TestTubeSubscriber(AMOUNT));
        int count = 0;
        Optional<TubeSubscriber<Update, BotApiMethod<?>>> maybeSubscriber;
        do{
            maybeSubscriber = extractor.getNext();
            if (maybeSubscriber.isPresent()){
                count++;
            }
        } while (maybeSubscriber.isPresent());

        assertThat(count).isEqualTo(AMOUNT);
    }

    private static class TestTubeSubscriber implements TubeSubscriber<Update, BotApiMethod<?>> {
        private int amount;

        public TestTubeSubscriber(int amount) {
            this.amount = amount;
        }

        @Override
        public Optional<TubeSubscriber<Update, BotApiMethod<?>>> getNext() {
            return amount-- > 0 ? Optional.of(this) : Optional.empty();
        }
    }
}
