package ru.kpn.tube.subscriber;

import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.strategy.SubscriberStrategy;
import ru.kpn.tube.strategy.none.NoneSubscriberStrategy;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SubscriberFactoryImplTest {

    @Autowired
    private SubscriberFactory<Update, BotApiMethod<?>, Integer> factory;

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
        SubscriberStrategy<Update, BotApiMethod<?>> strategy = getStrategy(factory);
        assertThat(strategy).isEqualTo(expectedStrategy);
    }

    @Test
    void shouldCheckMethodReset() {
        factory.strategy(expectedStrategy).comparator(expectedComparator).reset();
        assertThat(getStrategy(factory)).isNull();
        assertThat(getComparator(factory)).isNull();
    }

    @SneakyThrows
    private SubscriberStrategy<Update, BotApiMethod<?>> getStrategy(SubscriberFactory<Update, BotApiMethod<?>, Integer> factory) {
        Field field = factory.getClass().getDeclaredField("strategy");
        field.setAccessible(true);
        return (SubscriberStrategy<Update, BotApiMethod<?>>) ReflectionUtils.getField(field, factory);
    }

    @SneakyThrows
    private Comparator<Integer> getComparator(SubscriberFactory<Update, BotApiMethod<?>, Integer> factory) {
        Field field = factory.getClass().getDeclaredField("comparator");
        field.setAccessible(true);
        return (Comparator<Integer>) ReflectionUtils.getField(field, factory);
    }

    private static class TestStrategy implements SubscriberStrategy<Update, BotApiMethod<?>>{
        @Override
        public Optional<BotApiMethod<?>> execute(Update value) {
            return Optional.empty();
        }

        @Override
        public Integer getPriority() {
            return SubscriberStrategy.super.getPriority();
        }
    }

    private static class TestComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer p0, Integer p1) {
            if (Objects.equals(p0, p1)){
                return 0;
            }
            return p0 > p1 ? 1 : -1;
        }
    }
}
