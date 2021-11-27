// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.builder;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.kpn.objectExtraction.datum.StrategyInitDatum;
//import ru.kpn.objectExtraction.factory.ObjectFactory;
//import ru.kpn.objectExtraction.factory.StrategyInitFactory;
//import ru.kpn.objectExtraction.result.Result;
//import ru.kpn.rawMessage.BotRawMessageFactory;
//import ru.kpn.rawMessage.RawMessage;
//import ru.kpn.rawMessage.RawMessageFactory;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class StrategyInitBuilderTest {
//
//    private static final String KEY = "some.key";
//
//    private final RawMessageFactory<String> rawMessageFactory = new BotRawMessageFactory();
//
//    private StrategyInitBuilder builder;
//
//    @BeforeEach
//    void setUp() {
//        builder = new StrategyInitBuilder(new TestStrategyInitFactory(), rawMessageFactory);
//    }
//
//    @Test
//    void shouldCheckCreationAttemptWithDatumEqNull() {
//        RawMessage<String> expectedRawMessage = rawMessageFactory.create("data.notExist.forSth").add(KEY);
//        Result<Integer> result = builder.start(KEY).doScenario().build();
//        assertThat(result.getSuccess()).isFalse();
//        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
//    }
//
//    @Test
//    void shouldCheckCreationAttemptWithWrongArgs() {
//        RawMessage<String> expectedRawMessage = rawMessageFactory.create("arguments.invalid.forSth").add(KEY);
//        StrategyInitDatum datum = new StrategyInitDatum();
//        Result<Integer> result = builder.start(KEY).datum(datum).doScenario().build();
//        assertThat(result.getSuccess()).isFalse();
//        assertThat(result.getRawMessage()).isEqualTo(expectedRawMessage);
//    }
//
//    @Test
//    void shouldCheckCreation() {
//        StrategyInitDatum datum = new StrategyInitDatum();
//        datum.setPriority(123);
//        Result<Integer> result = builder.start(KEY).datum(datum).doScenario().build();
//        assertThat(result.getSuccess()).isTrue();
//        assertThat(result.getValue()).isNotNull();
//    }
//
//
//    private static class TestStrategyInitFactory implements ObjectFactory<StrategyInitFactory.Type, Integer> {
//        @Override
//        public Integer create(StrategyInitFactory.Type type, Object... args) {
//            return (int) args[0];
//        }
//    }
//}
