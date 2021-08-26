package ru.kpn.tube.calculator;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.tube.extractor.TubeSubscriberExtractorImpl;
import ru.kpn.tube.subscriber.TubeSubscriber;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class SubscriberCalculatorFactoryImplTest {

    private final SubscriberCalculatorFactoryImpl factory = new SubscriberCalculatorFactoryImpl();
    private final TubeSubscriber<Update, BotApiMethod<?>> subscriber = new TestSubscriber();

    @Test
    void shouldCheckCalculatorCreation() {
        SubscriberCalculatorImpl calculator = (SubscriberCalculatorImpl) factory.create(subscriber);
        TubeSubscriberExtractorImpl extractor = getExtractor(calculator);
        TestSubscriber s = getSubscriber(extractor);

        assertThat(s).isEqualTo(subscriber);
    }

    @SneakyThrows
    private TubeSubscriberExtractorImpl getExtractor(SubscriberCalculatorImpl calculator) {
        Field field = calculator.getClass().getDeclaredField("extractor");
        field.setAccessible(true);
        return (TubeSubscriberExtractorImpl) ReflectionUtils.getField(field, calculator);
    }

    @SneakyThrows
    private TestSubscriber getSubscriber(TubeSubscriberExtractorImpl extractor) {
        Field field = extractor.getClass().getDeclaredField("subscriber");
        field.setAccessible(true);
        return (TestSubscriber) ReflectionUtils.getField(field, extractor);
    }

    private static class TestSubscriber implements TubeSubscriber<Update, BotApiMethod<?>> {
    }
}