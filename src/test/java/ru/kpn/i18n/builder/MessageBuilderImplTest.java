package ru.kpn.i18n.builder;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;
import ru.kpn.i18n.I18n;
import ru.kpn.i18n.adapter.arguments.ArgumentsAdapter;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageBuilderImplTest {

    private static final String TEST_CODE = "test code";

    private MessageBuilderImpl builder;

    @BeforeEach
    void setUp() {
        builder = new MessageBuilderImpl(TEST_CODE, new TestI18n(), new TestArgumentsAdapter());
    }

    @Test
    void shouldCheckArgMethod() {
        List<Object> expectedArguments = List.of("hello", 1, 0.89);
        for (Object arg : expectedArguments) {
            builder.arg(arg);
        }
        List<Object> arguments = getArguments(builder);
        assertThat(expectedArguments).isEqualTo(arguments);
    }

    @Test
    void shouldCheckBuilding() {
        List<Integer> args = List.of(1, 2, 3);
        String expectedMessage = String.format("%s %s %s %s", TEST_CODE, args.get(0), args.get(1), args.get(2));
        for (Integer arg : args) {
            builder.arg(arg);
        }
        String message = builder.build();

        assertThat(expectedMessage).isEqualTo(message);
    }

    @SneakyThrows
    private List<Object> getArguments(MessageBuilderImpl builder) {
        Field field = builder.getClass().getDeclaredField("arguments");
        field.setAccessible(true);
        return (List<Object>) ReflectionUtils.getField(field, builder);
    }

    private static class TestI18n implements I18n {

        @Override
        public String get(String code, Object... args) {
            StringBuilder sb = new StringBuilder(code);
            for (Object arg : args) {
                sb.append(" ").append(arg);
            }
            return sb.toString();
        }
    }

    private static class TestArgumentsAdapter implements ArgumentsAdapter {

        @Override
        public String getCode() {
            return null;
        }

        @Override
        public Object[] adapt(Object[] sourceObjects) {
            return sourceObjects;
        }
    }
}
