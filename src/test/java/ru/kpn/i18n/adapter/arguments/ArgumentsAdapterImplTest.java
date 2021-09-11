package ru.kpn.i18n.adapter.arguments;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.kpn.i18n.adapter.argument.ArgumentAdapter;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentsAdapterImplTest {

    private final Random random = new Random();

    @RepeatedTest(100)
    void shouldCheckCodeGetting() {
        String code = String.format("code%s", random.nextInt());
        ArgumentsAdapterImpl adapter = ArgumentsAdapterImpl.builder().code(code).build();
        assertThat(code).isEqualTo(adapter.getCode());
    }

    @Test
    void shouldCheckAdaptation() {
        Object[] objects = {0, 1, 2};
        int delta = 10;
        ArgumentsAdapterImpl adapter = ArgumentsAdapterImpl.builder()
                .resizer(new TestResizer(objects))
                .adapter(0, new TextAdapter(delta))
                .adapter(1, new TextAdapter(delta))
                .adapter(2, new TextAdapter(delta))
                .build();

        int length = objects.length;
        Object[] expectedObjects = new Object[length];
        for (int i = 0; i < length; i++) {
            expectedObjects[i] = (Integer)objects[i] + delta;
        }

        assertThat(expectedObjects).isEqualTo(adapter.adapt(objects));
    }

    @AllArgsConstructor
    private static class TextAdapter implements ArgumentAdapter {

        private final int delta;

        @Override
        public Object adapt(Object o) {
            Integer value = (Integer) o;
            value += delta;
            return value;
        }
    }

    @AllArgsConstructor
    private static class TestResizer implements Function<Object[], Object[]> {

        private final Object[] objects;

        @Override
        public Object[] apply(Object[] objects) {
            return this.objects;
        }
    }
}
