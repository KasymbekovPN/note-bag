package ru.kpn.i18n.builder;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapter;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageBuilderFactoryImplTest {

    private static final String TEST_CODE = "test.code";
    private static final String OTHER_TEST_CODE = "other.test.code";

    private MessageBuilderFactoryImpl factory;

    @BeforeEach
    void setUp() {
        factory = MessageBuilderFactoryImpl.builder()
                .i18n(new TestI18n())
                .adapter(new TestArgumentsAdapter(TEST_CODE))
                .build();
    }

    @Test
    void shouldCreateWithDefaultAdapter() {
        String expectedMessage = new Formatter().format(OTHER_TEST_CODE, 1, 2, 3);
        MessageBuilder builder = factory.create(OTHER_TEST_CODE);
        String message = builder.arg(1).arg(2).arg(3).build();
        assertThat(expectedMessage).isEqualTo(message);
    }

    @Test
    void shouldCreateWithNotDefaultAdapter() {
        String expectedMessage = new Formatter().format(TEST_CODE, 101, 102, 103);
        MessageBuilder builder = factory.create(TEST_CODE);
        String message = builder.arg(1).arg(2).arg(3).build();
        assertThat(expectedMessage).isEqualTo(message);
    }

    private static class TestI18n implements I18n {
        @Override
        public String get(String code, Object... args) {
            return new Formatter().format(code, args);
        }
    }

    @AllArgsConstructor
    private static class TestArgumentsAdapter implements ArgumentsAdapter {
        private final String code;

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public Object[] adapt(Object[] sourceObjects) {
            Object[] objects = new Object[sourceObjects.length];
            for (int i = 0; i < objects.length; i++) {
                objects[i] = (Integer)sourceObjects[i] + 100;
            }

            return objects;
        }
    }

    private static class Formatter{

        public String format(String code, Object... args) {
            StringBuilder sb = new StringBuilder(code);
            for (Object arg : args) {
                sb.append(" ").append(arg);
            }
            return sb.toString();
        }
    }
}
