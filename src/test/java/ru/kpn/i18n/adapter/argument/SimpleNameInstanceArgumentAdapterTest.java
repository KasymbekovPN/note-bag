package ru.kpn.i18n.adapter.argument;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleNameInstanceArgumentAdapterTest {

    private final ArgumentAdapter adapter = new SimpleNameInstanceArgumentAdapter();

    @Test
    void shouldAdaptIfArgumentIsNull() {
        Object adaptResult = adapter.adapt(null);
        assertThat(adaptResult).isEqualTo("null");
    }

    @Test
    void shouldAdaptIfArgumentIsNotNull() {
        SimpleNameClassArgumentAdapterTest instance = new SimpleNameClassArgumentAdapterTest();
        Object adaptResult = adapter.adapt(instance);
        assertThat(adaptResult).isEqualTo(instance.getClass().getSimpleName());
    }
}
