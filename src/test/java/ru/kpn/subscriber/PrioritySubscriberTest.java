package ru.kpn.subscriber;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.Strategy;
import utils.UpdateInstanceBuilder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PrioritySubscriberTest {

    private static final Integer SIZE = 10;
    private static final Random RANDOM = new Random();

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
        PrioritySubscriber s1 = new PrioritySubscriber(new TestStrategy(1));
        assertThat(s1.compareTo(1)).isZero();
        assertThat(s1.compareTo(0)).isEqualTo(1);
        assertThat(s1.compareTo(2)).isEqualTo(-1);
    }

    @Test
    void shouldCheckCustomComparator() {
        PrioritySubscriber s1 = new PrioritySubscriber(new TestStrategy(1), new TestComparator());
        assertThat(s1.compareTo(1)).isZero();
        assertThat(s1.compareTo(0)).isEqualTo(-1);
        assertThat(s1.compareTo(2)).isEqualTo(1);
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
    }

    private static class TestComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer i1, Integer i2) {
            if (Objects.equals(i1, i2)){
                return 0;
            }
            return i1 > i2 ? -1 : 1;
        }
    }
}
