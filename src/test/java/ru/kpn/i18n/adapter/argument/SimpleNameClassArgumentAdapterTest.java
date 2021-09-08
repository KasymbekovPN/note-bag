package ru.kpn.i18n.adapter.argument;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleNameClassArgumentAdapterTest {
    
    private final ArgumentAdapter adapter = new SimpleNameClassArgumentAdapter();

    @Test
    void shouldAdaptIfArgumentIsNull() {
        Object adaptResult = adapter.adapt(null);
        assertThat(adaptResult).isEqualTo("null");
    }

    @Test
    void shouldAdaptIfArgumentIsNotClass() {
        Object adaptResult = adapter.adapt("");
        assertThat(adaptResult).isEqualTo("null");
    }

    @Test
    void shouldAdaptIfArgumentIsClass() {
        Object adaptResult = adapter.adapt(this.getClass());
        assertThat(adaptResult).isEqualTo(this.getClass().getSimpleName());
    }
}
