package ru.kpn.matcher;

import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantMatcherTest {

    private final Function<String, Boolean> falseMatcher = new ConstantMatcher(false);
    private final Function<String, Boolean> trueMatcher = new ConstantMatcher(true);

    @RepeatedTest(100)
    void shouldCheckMatchingForFalse() {
        Random random = new Random();
        assertThat(falseMatcher.apply(String.valueOf(random.nextInt()))).isFalse();
    }

    @RepeatedTest(100)
    void shouldCheckMatchingForTrue() {
        Random random = new Random();
        assertThat(trueMatcher.apply(String.valueOf(random.nextInt()))).isTrue();
    }
}