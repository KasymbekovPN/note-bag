package ru.kpn.objectFactory.result;

import org.junit.jupiter.api.Test;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

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
        RawMessage<String> status = new BotRawMessage("code").add(123);
        ValuedResult<String> result = new ValuedResult<>(status);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getStatus()).isEqualTo(status);
    }
}