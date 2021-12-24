package ru.kpn.objectFactory.result;

import org.junit.jupiter.api.Test;
import ru.kpn.seed.Seed;
import ru.kpn.seed.StringSeedBuilderFactory;

import static org.assertj.core.api.Assertions.assertThat;

class ValuedResultTest {

    @Test
    void shouldCheckCreationOfSuccessResult() {
        final String value = "value";
        ValuedResult<String> result = new ValuedResult<>(value);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(value);
    }

    @Test
    void shouldCheckCreationOfFailResult() {
        final Seed<String> status = StringSeedBuilderFactory.builder().code("code").arg(123).build();
        ValuedResult<String> result = new ValuedResult<>(false, status);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(status);
    }
}