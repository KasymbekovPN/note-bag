package ru.kpn.seed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StringSeedTest {

    private static final String CODE = "some.code";
    private static final Object[] ARGS = new Object[]{1, 2, 3};

    @Test
    void shouldCheckCodeGetting() {
        Seed<String> source = new StringSeed(CODE);
        assertThat(CODE).isEqualTo(source.getCode());
    }

    @Test
    void shouldCheckArgsGetting() {
        StringSeed source = new StringSeed(CODE, ARGS);
        assertThat(ARGS).isEqualTo(source.getArgs());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckEqual.csv")
    void shouldCheckEqualAndHashCode(String code0, String arg00, String arg01,
                                     String code1, String arg10, String arg11, Boolean expectedResult) {
        final StringSeed statusSeed0 = new StringSeed(code0, new Object[]{arg00, arg01});
        final StringSeed statusSeed1 = new StringSeed(code1, new Object[]{arg10, arg11});
        assertThat(statusSeed0.equals(statusSeed1)).isEqualTo(expectedResult);
        assertThat(statusSeed0.hashCode() == statusSeed1.hashCode()).isEqualTo(expectedResult);
    }
}
