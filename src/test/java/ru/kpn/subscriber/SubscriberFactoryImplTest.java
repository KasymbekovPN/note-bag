package ru.kpn.subscriber;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategy.strategies.Strategy;
import ru.kpn.statusSeed.RawMessageOld;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SubscriberFactoryImplTest {

    @Autowired
    private SubscriberFactory<Update, BotApiMethod<?>> factory;

    private TestStrategy expectedStrategy;
    private TestComparator expectedComparator;

    @BeforeEach
    void setUp() {
        expectedComparator = new TestComparator();
        expectedStrategy = new TestStrategy();
    }

    @Test
    void shouldCheckMethodComparator() {
        factory.comparator(expectedComparator);
        Comparator<Integer> comparator = getComparator(factory);
        assertThat(comparator).isEqualTo(expectedComparator);
    }

    @Test
    void shouldCheckMethodStrategy() {
        factory.strategy(expectedStrategy);
        Strategy<Update, BotApiMethod<?>> strategy = getStrategy(factory);
        assertThat(strategy).isEqualTo(expectedStrategy);
    }

    @Test
    void shouldCheckMethodReset() {
        factory.strategy(expectedStrategy).comparator(expectedComparator).reset();
        assertThat(getStrategy(factory)).isNull();
        assertThat(getComparator(factory)).isNull();
    }

    @SneakyThrows
    private Strategy<Update, BotApiMethod<?>> getStrategy(SubscriberFactory<Update, BotApiMethod<?>> factory) {
        Field field = factory.getClass().getDeclaredField("strategy");
        field.setAccessible(true);
        return (Strategy<Update, BotApiMethod<?>>) ReflectionUtils.getField(field, factory);
    }

    @SneakyThrows
    private Comparator<Integer> getComparator(SubscriberFactory<Update, BotApiMethod<?>> factory) {
        Field field = factory.getClass().getDeclaredField("comparator");
        field.setAccessible(true);
        return (Comparator<Integer>) ReflectionUtils.getField(field, factory);
    }

    private static class TestStrategy implements Strategy<Update, BotApiMethod<?>> {
        @Override
        public Optional<BotApiMethod<?>> execute(Update value) {
            return Optional.empty();
        }

        @Override
        public Integer getPriority() {
            return Strategy.super.getPriority();
        }

        @Override
        public RawMessageOld<String> runAndGetRawMessage(Update value) {
            return null;
        }
    }

    private static class TestComparator implements Comparator<Subscriber<Update, BotApiMethod<?>>> {
        @Override
        public int compare(Subscriber<Update, BotApiMethod<?>> s1, Subscriber<Update, BotApiMethod<?>> s2) {
            if (Objects.equals(s1.getPriority(), s2.getPriority())){
                return 0;
            }
            return s1.getPriority() > s2.getPriority() ? 1 : -1;
        }
    }
}
