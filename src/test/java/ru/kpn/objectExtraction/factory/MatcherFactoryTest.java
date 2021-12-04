package ru.kpn.objectExtraction.factory;

public class MatcherFactoryTest {


//        private final EnumMap<ExtractorDatumType.ALLOWED_TYPE, Integer> expectedValues
//            = new EnumMap<>(ExtractorDatumType.ALLOWED_TYPE.class);
//    private final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();
//
//    private ExtractorFactory factory;
//
//    @SneakyThrows
//    @BeforeEach
//    void setUp() {
//        ExtractorFactory.Builder builder = ExtractorFactory.builder();
//        int value = 0;
//        for (ExtractorDatumType.ALLOWED_TYPE allowedType : ExtractorDatumType.ALLOWED_TYPE.values()) {
//            builder.creator(new ExtractorDatumType(allowedType.name()), new TestCreator(value));
//            expectedValues.put(allowedType, value++);
//        }
//        factory = builder.build();
//    }
//
//    @Test
//    void shouldCheckAttemptOfNotCompletelyCreationOfFactory() {
//        Throwable throwable = catchThrowable(() -> {
//            ExtractorFactory.builder().build();
//        });
//        assertThat(throwable).isInstanceOf(Exception.class);
//    }
//
//    @Test
//    void shouldCheckCreation() {
//        for (ExtractorDatumType.ALLOWED_TYPE allowedType : ExtractorDatumType.ALLOWED_TYPE.values()) {
//            ExtractorDatum datum = new ExtractorDatum();
//            datum.setType(allowedType.name());
//            Result<Function<Update, String>, RawMessage<String>> result = factory.create(datum);
//            assertThat(new TestExtractor(expectedValues.get(allowedType))).isEqualTo(result.getValue());
//        }
//    }
//
//    @Test
//    void shouldCheckCreationAttemptWithWrongType() {
//        String wrong = "WRONG";
//        RawMessage<String> expectedStatus = messageFactory.create("strategyInitFactory.wrongType").add(wrong);
//        ExtractorDatum datum = new ExtractorDatum();
//        datum.setType(wrong);
//         Result<Function<Update, String>, RawMessage<String>> result = factory.create(datum);
//        assertThat(result.getSuccess()).isFalse();
//        assertThat(result.getStatus()).isEqualTo(expectedStatus);
//    }
//
//    @AllArgsConstructor
//    @Getter
//    private static class TestCreator implements Creator<ExtractorDatum, Function<Update, String>, RawMessage<String>> {
//        private final int value;
//
//        @Override
//        public Result<Function<Update, String>, RawMessage<String>> create(ExtractorDatum datum) {
//            return OptimisticResult.<Function<Update, String>>builder().value(new TestExtractor(value)).build();
//        }
//    }
//
//    @AllArgsConstructor
//    @EqualsAndHashCode
//    private static class TestExtractor implements Function<Update, String>{
//        private final int value;
//
//        @Override
//        public String apply(Update update) {
//            return null;
//        }
//    }


}
