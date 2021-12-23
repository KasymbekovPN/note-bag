package ru.kpn.rawMessage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleRawSourceTest {

    private static final String CODE = "some.code";
    private static final Object[] ARGS = new Object[]{1, 2, 3};

    @Test
    void shouldCheckCodeGetting() {
        RawSource<String> source = new SimpleRawSource(CODE);
        assertThat(CODE).isEqualTo(source.getCode());
    }

    @Test
    void shouldCheckArgsGetting() {
        SimpleRawSource source = new SimpleRawSource(CODE, ARGS);
        assertThat(ARGS).isEqualTo(source.getArgs());
    }
}
