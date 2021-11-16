package ru.kpn.objectExtraction.extractor;

import lombok.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kpn.objectExtraction.builder.Builder;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.objectExtraction.result.ResultImpl;
import ru.kpn.rawMessage.RawMessage;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class YmlExtractorTest {

    private static final String KEY0 = "key0";
    private static final String KEY1 = "key1";
    private static final String VALUE0 = "value0";

    private YmlExtractor<TestDatum, String> extractor;

    @BeforeEach
    void setUp() {
        HashMap<String, TestDatum> initData = new HashMap<>(){{
            put(KEY0, new TestDatum(VALUE0));
        }};
        extractor = new YmlExtractor<>(initData, new TestBuilder());
    }

    @Test
    void shouldCheckCreation() {
        Result<String> result = extractor.getOrCreate(KEY0);
        assertThat(result.getSuccess()).isTrue();
        assertThat(result.getValue()).isEqualTo(VALUE0);
    }

    @Test
    void shouldCheckInvalidAttemptOfCreation() {
        Result<String> result = extractor.getOrCreate(KEY1);
        assertThat(result.getSuccess()).isFalse();
        assertThat(result.getRawMessage()).isEqualTo(new TestRawMessage());
    }

    @AllArgsConstructor
    @Getter
    private static class TestDatum {
        private String value;
    }

    private static class TestBuilder implements Builder<TestDatum, String> {
        private TestDatum datum;

        @Override
        public Builder<TestDatum, String> key(String key) {
            return this;
        }

        @Override
        public Builder<TestDatum, String> datum(TestDatum datum) {
            this.datum = datum;
            return this;
        }

        @Override
        public Builder<TestDatum, String> doScenario() {
            return this;
        }

        @Override
        public Result<String> build() {
            ResultImpl<String> result = new ResultImpl<>();
            if (datum == null){
                result.setSuccess(false);
                result.setRawMessage(new TestRawMessage());
            } else {
                result.setSuccess(true);
                result.setValue(datum.getValue());
            }
            datum = null;
            return result;
        }
    }

    @EqualsAndHashCode
    private static class TestRawMessage implements RawMessage<String> {
        private static final String CODE = "err.code";
        private final String code;

        public TestRawMessage() {
            this.code = CODE;
        }

        @Override
        public String getCode() {
            return code;
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