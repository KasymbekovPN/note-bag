package ru.kpn.subscriber;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.Strategy;
import utils.UpdateInstanceBuilder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PrioritySubscriberOldTest {

    private static final int AMOUNT = 10;

    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
    }

    @Test
    void shouldCheckSetGetNextMethods() {
        TreeSet<Integer> priorities = new TreeSet<>(new TextComparator());
        Subscriber<Update, BotApiMethod<?>> ps = null;
        for (int i = 0; i < AMOUNT; i++) {
            int priority = random.nextInt();
            if (ps == null){
                ps = new PrioritySubscriberOld(new TestStrategyWithPriority(priority), new PrioritySubscriberOld.DefaultComparator());
            } else {
                ps = ps.setNext(new PrioritySubscriberOld(new TestStrategyWithPriority(priority), new PrioritySubscriberOld.DefaultComparator()));
            }
            priorities.add(priority);
        }

        int counter = 0;
        int size = priorities.size();
        for (Integer priority : priorities) {
            assertThat(priority).isEqualTo(ps.getPriority());
            if (counter++ < size - 1){
                Optional<Subscriber<Update, BotApiMethod<?>>> maybeNext = ps.getNext();
                assertThat(maybeNext).isPresent();
                ps = maybeNext.get();
            }
        }
    }

    @Test
    void shouldCheckExecuteStrategy() {
        TestStrategy strategy = new TestStrategy();
        Subscriber<Update, BotApiMethod<?>> subscriber = new PrioritySubscriberOld(strategy, null);
        subscriber.executeStrategy(new UpdateInstanceBuilder().build());
        assertThat(strategy.getFlag()).isTrue();
    }

    @RepeatedTest(100)
    void shouldCheckPriority() {
        int priority = random.nextInt();
        Subscriber<Update, BotApiMethod<?>> subscriber = new PrioritySubscriberOld(new TestStrategyWithPriority(priority), null);
        assertThat(priority).isEqualTo(subscriber.getPriority());
    }

    private static class TextComparator implements Comparator<Integer>{

        private static final Comparator<Integer> COMPARATOR = new PrioritySubscriberOld.DefaultComparator();

        @Override
        public int compare(Integer integer, Integer t1) {
            return -1 * COMPARATOR.compare(integer, t1);
        }
    }

    @Getter
    private static class TestStrategy implements Strategy<Update, BotApiMethod<?>> {

        private Boolean flag = false;

        @Override
        public Optional<BotApiMethod<?>> execute(Update value) {
            flag = true;
            return Optional.empty();
        }
    }

    private static class TestStrategyWithPriority implements Strategy<Update, BotApiMethod<?>> {

        private final Integer priority;

        public TestStrategyWithPriority(Integer priority) {
            this.priority = priority;
        }

        @Override
        public Optional<BotApiMethod<?>> execute(Update value) {
            return Optional.empty();
        }

        @Override
        public Integer getPriority() {
            return priority;
        }
    }
}
