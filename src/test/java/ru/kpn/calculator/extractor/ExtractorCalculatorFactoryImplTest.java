package ru.kpn.calculator.extractor;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.extractor.IterableExtractor;
import ru.kpn.subscriber.Subscriber;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 25.09.2021 del
//public class ExtractorCalculatorFactoryImplTest {
//
//    private final ExtractorCalculatorFactoryImpl factory = new ExtractorCalculatorFactoryImpl();
//    private final Subscriber<Update, BotApiMethod<?>> subscriber = new TestSubscriber();
//
//    @Test
//    void shouldCheckCalculatorCreation() {
//        SimpleExtractorCalculator calculator = (SimpleExtractorCalculator) factory.create(subscriber);
//        IterableExtractor extractor = getExtractor(calculator);
//        TestSubscriber s = getSubscriber(extractor);
//
//        assertThat(s).isEqualTo(subscriber);
//    }
//
//    @SneakyThrows
//    private IterableExtractor getExtractor(SimpleExtractorCalculator calculator) {
//        Field field = calculator.getClass().getDeclaredField("extractor");
//        field.setAccessible(true);
//        return (IterableExtractor) ReflectionUtils.getField(field, calculator);
//    }
//
//    @SneakyThrows
//    private TestSubscriber getSubscriber(IterableExtractor extractor) {
//        Field field = extractor.getClass().getDeclaredField("subscriber");
//        field.setAccessible(true);
//        return (TestSubscriber) ReflectionUtils.getField(field, extractor);
//    }
//
//    private static class TestSubscriber implements Subscriber<Update, BotApiMethod<?>> {
//        @Override
//        public int compareTo(Integer integer) {
//            return 0;
//        }
//    }
//}