package ru.kpn.objectExtraction.result;

import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.Test;
import ru.kpn.rawMessage.RawMessage;

import static org.assertj.core.api.Assertions.assertThat;

class ResultImplTest {

    @Test
    void shouldCheckSuccess() {
        ResultImpl<String> result = new ResultImpl<>();
        assertThat(result.getSuccess()).isNull();
        result.setSuccess(true);
        assertThat(result.getSuccess()).isTrue();
    }

    @Test
    void shouldCheckRawMessage() {
        ResultImpl<String> result = new ResultImpl<>();
        assertThat(result.getRawMessage()).isNull();
        result.setRawMessage(new TestRawMessage());
        assertThat(result.getRawMessage()).isEqualTo(new TestRawMessage());
    }

    @Test
    void shouldCheckValue() {
        String value = "some text";
        ResultImpl<String> result = new ResultImpl<>();
        assertThat(result.getValue()).isNull();
        result.setValue(value);
        assertThat(result.getValue()).isEqualTo(value);
    }

    @EqualsAndHashCode
    private static class TestRawMessage implements RawMessage<String> {

        private static final String CODE = "some.code";

        private final String code = CODE;

        @Override
        public String getCode() {
            return CODE;
        }

        @Override
        public RawMessage<String> add(Object o) {
            return this;
        }

        @Override
        public Object[] getArgs() {
            return new Object[0];
        }


    }
}