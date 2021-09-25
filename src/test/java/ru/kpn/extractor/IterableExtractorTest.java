package ru.kpn.extractor;

// TODO: 25.09.2021 del
//import org.junit.jupiter.api.Test;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import ru.kpn.subscriber.Subscriber;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class IterableExtractorTest {
//
//    private static final int AMOUNT = 10;
//
//    @Test
//    void shouldCheckGetNextMethod() {
//        IterableExtractor extractor = new IterableExtractor(new TestSubscriber(AMOUNT));
//        int count = -1;
//        Optional<Subscriber<Update, BotApiMethod<?>>> maybeSubscriber;
//        do{
//            maybeSubscriber = extractor.getNext();
//            if (maybeSubscriber.isPresent()){
//                count++;
//            }
//        } while (maybeSubscriber.isPresent());
//
//        assertThat(count).isEqualTo(AMOUNT);
//    }
//
//    private static class TestSubscriber implements Subscriber<Update, BotApiMethod<?>> {
//        private int amount;
//
//        public TestSubscriber(int amount) {
//            this.amount = amount;
//        }
//
//        @Override
//        public int compareTo(Integer integer) {
//            return 0;
//        }
//
//        @Override
//        public Optional<Subscriber<Update, BotApiMethod<?>>> getNext() {
//            return amount-- > 0 ? Optional.of(this) : Optional.empty();
//        }
//    }
//}
