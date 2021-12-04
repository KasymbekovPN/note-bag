package ru.kpn.objectExtraction.result;

import org.junit.jupiter.api.Test;
import ru.kpn.rawMessage.BotRawMessage;

import static org.assertj.core.api.Assertions.assertThat;

public class OptimisticResultTest {

    @Test
    void shouldCheckDefaultSuccessValue() {
        OptimisticResult<String> result = OptimisticResult.<String>builder().value("").build();
        assertThat(result.getSuccess()).isTrue();
    }

    @Test
    void shouldCheckSuccessValueSetting() {
        OptimisticResult<String> result = OptimisticResult.<String>builder().success(false).build();
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckDefaultStatusValue() {
        OptimisticResult<String> result = OptimisticResult.<String>builder().value("").build();
        assertThat(result.getStatus().getCode()).isEmpty();
    }

    @Test
    void shouldCheckStatusSetting() {
        String code = "some.code";
        OptimisticResult<String> result = OptimisticResult.<String>builder().status(new BotRawMessage(code)).build();
        assertThat(result.getStatus().getCode()).isEqualTo(code);
    }

    @Test
    void shouldCheckDefaultValue() {
        OptimisticResult<String> result = OptimisticResult.<String>builder().build();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isNull();
    }

    @Test
    void shouldCheckAttemptOfNonConsistCreation() {
        // TODO: 04.12.2021 restore 
//        OptimisticResult<String> result = OptimisticResult.<String>builder().success(true).value(null).build();
//        assertThat(result.getSuccess()).isFalse();
//        assertThat(result.getValue()).isNull();
//        assertThat(result.getStatus().getCode()).isEqualTo("success==true.value==null.onResultCreation");
    }

    @Test
    void shouldCheckCreation() {
        String value = "some.value";
        OptimisticResult<String> result = OptimisticResult.<String>builder().value(value).build();
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(value);
    }
}
