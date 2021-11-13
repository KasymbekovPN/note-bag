package ru.kpn.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.Strategy;
import ru.kpn.rawMessage.RawMessage;
import utils.UpdateInstanceBuilder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PrioritySubscriberTest {

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution.csv")
    void shouldCheckStrategyExecution(Long chatId, Boolean expectedIsPresent) {
        Update update = new UpdateInstanceBuilder().chatId(chatId).build();
        PrioritySubscriber subscriber = new PrioritySubscriber(new TestStrategy(0));
        Optional<BotApiMethod<?>> result = subscriber.executeStrategy(update);
        assertThat(result.isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckDefaultComparator() {
        PrioritySubscriber s1 = createSubscriber(1);
        assertThat(s1.compareTo(createSubscriber(1))).isZero();
        assertThat(s1.compareTo(createSubscriber(0))).isEqualTo(1);
        assertThat(s1.compareTo(createSubscriber(2))).isEqualTo(-1);
    }

    private PrioritySubscriber createSubscriber(int priority) {
        return new PrioritySubscriber(new TestStrategy(priority));
    }

    @Test
    void shouldCheckCustomComparator() {
        PrioritySubscriber s1 = createSubscriberWithComparator(1);
        assertThat(s1.compareTo(createSubscriberWithComparator(1))).isZero();
        assertThat(s1.compareTo(createSubscriberWithComparator(0))).isEqualTo(-1);
        assertThat(s1.compareTo(createSubscriberWithComparator(2))).isEqualTo(1);
    }

    private PrioritySubscriber createSubscriberWithComparator(int priority) {
        return new PrioritySubscriber(new TestStrategy(priority), new TestComparator());
    }

    private static class TestStrategy implements Strategy<Update, BotApiMethod<?>> {

        private final int priority;

        public TestStrategy(int priority) {
            this.priority = priority;
        }

        @Override
        public Integer getPriority() {
            return priority;
        }

        @Override
        public Optional<BotApiMethod<?>> execute(Update value) {
            if (value.getMessage().getChatId().equals(1L)){
                return Optional.of(new SendMessage("123", "text"));
            }
            return Optional.empty();
        }

        @Override
        public RawMessage<String> runAndGetAnswer(Update value) {
            return null;
        }
    }

    private static class TestComparator implements Comparator<Subscriber<Update, BotApiMethod<?>>> {
        @Override
        public int compare(Subscriber<Update, BotApiMethod<?>> s1, Subscriber<Update, BotApiMethod<?>> s2) {
            if (Objects.equals(s1.getPriority(), s2.getPriority())){
                return 0;
            }
            return s1.getPriority() > s2.getPriority() ? -1 : 1;
        }
    }
}
