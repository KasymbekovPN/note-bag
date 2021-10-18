package ru.kpn.matcher;

import org.junit.jupiter.api.RepeatedTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import utils.UpdateInstanceBuilder;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstantMatcherTest {

    private static final Random RANDOM = new Random();

    private final Function<Update, Boolean> falseMatcher = new ConstantMatcher(false);
    private final Function<Update, Boolean> trueMatcher = new ConstantMatcher(true);

    @RepeatedTest(100)
    void shouldCheckMatchingForFalse() {
        Update update = new UpdateInstanceBuilder().text(String.valueOf(RANDOM.nextInt())).build();
        assertThat(falseMatcher.apply(update)).isFalse();
    }

    @RepeatedTest(100)
    void shouldCheckMatchingForTrue() {
        Update update = new UpdateInstanceBuilder().text(String.valueOf(RANDOM.nextInt())).build();
        assertThat(trueMatcher.apply(update)).isTrue();
    }
}
