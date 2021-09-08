package ru.kpn.i18n.adapter.argument;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class NullArgumentAdapterTest {

    private final ArgumentAdapter adapter = new NullArgumentAdapter();
    private final Random random = new Random();

    @Test
    void shouldAdaptNullInstance() {
        Object adaptResult = adapter.adapt(null);
        assertThat(adaptResult).isEqualTo("null");
    }

    @RepeatedTest(100)
    void shouldAdaptNotNullInstance() {
        Object object = random.nextInt();
        Object adaptResult = adapter.adapt(object);
        assertThat(adaptResult).isEqualTo(object);
    }
}
