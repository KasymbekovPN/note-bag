package ru.kpn.tube.subscriber;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.strategy.SubscriberStrategy;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PriorityTubeSubscriberTest {

    private static final int AMOUNT = 10;

    private PriorityTubeSubscriber.PriorityTubeSubscriberBuilder builder;
    private Random random;

    @BeforeEach
    void setUp() {
        builder = PriorityTubeSubscriber.builder();
        random = new Random();
    }

    @Test
    void shouldCheckSetGetNextMethods() {
        TreeSet<Integer> priorities = new TreeSet<>(new TextComparator());
        TubeSubscriber<Update, BotApiMethod<?>> ps = null;
        for (int i = 0; i < AMOUNT; i++) {
            int priority = random.nextInt();
            if (ps == null){
                ps = builder.priority(priority).build();
            } else {
                ps = ps.setNext(builder.priority(priority).build());
            }
            priorities.add(priority);
        }

        int counter = 0;
        int size = priorities.size();
        for (Integer priority : priorities) {
            assertThat(priority).isEqualTo(ps.getPriority());
            if (counter++ < size - 1){
                Optional<TubeSubscriber<Update, BotApiMethod<?>>> maybeNext = ps.getNext();
                assertThat(maybeNext).isPresent();
                ps = maybeNext.get();
            }
        }
    }

    @Test
    void shouldCheckExecuteStrategy() {
        TestStrategy strategy = new TestStrategy();
        TubeSubscriber<Update, BotApiMethod<?>> subscriber = builder.strategy(strategy).build();
        subscriber.executeStrategy(new Update());
        assertThat(strategy.getFlag()).isTrue();
    }

    @RepeatedTest(100)
    void shouldCheckPriority() {
        int priority = random.nextInt();
        TubeSubscriber<Update, BotApiMethod<?>> subscriber = builder.priority(priority).build();
        assertThat(priority).isEqualTo(subscriber.getPriority());
    }

    private static class TextComparator implements Comparator<Integer>{

        private static final Comparator<Integer> COMPARATOR = new PriorityTubeSubscriber.DefaultComparator();

        @Override
        public int compare(Integer integer, Integer t1) {
            return -1 * COMPARATOR.compare(integer, t1);
        }
    }

    @Getter
    private static class TestStrategy implements SubscriberStrategy<Update, BotApiMethod<?>> {

        private Boolean flag = false;

        @Override
        public Optional<BotApiMethod<?>> execute(Update value) {
            flag = true;
            return Optional.empty();
        }
    }
}
