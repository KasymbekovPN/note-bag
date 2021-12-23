package ru.kpn.statusSeed.seed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StringStatusSeedTest {

    private static final String CODE = "some.code";
    private static final Object[] ARGS = new Object[]{1, 2, 3};

    @Test
    void shouldCheckCodeGetting() {
        StatusSeed<String> source = new StringStatusSeed(CODE);
        assertThat(CODE).isEqualTo(source.getCode());
    }

    @Test
    void shouldCheckArgsGetting() {
        StringStatusSeed source = new StringStatusSeed(CODE, ARGS);
        assertThat(ARGS).isEqualTo(source.getArgs());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckEqual.csv")
    void shouldCheckEqualAndHashCode(String code0, String arg00, String arg01,
                                     String code1, String arg10, String arg11, Boolean expectedResult) {
        final StringStatusSeed statusSeed0 = new StringStatusSeed(code0, new Object[]{arg00, arg01});
        final StringStatusSeed statusSeed1 = new StringStatusSeed(code1, new Object[]{arg10, arg11});
        assertThat(statusSeed0.equals(statusSeed1)).isEqualTo(expectedResult);
        assertThat(statusSeed0.hashCode() == statusSeed1.hashCode()).isEqualTo(expectedResult);
    }
}
