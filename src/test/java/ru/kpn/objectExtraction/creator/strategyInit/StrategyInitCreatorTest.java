package ru.kpn.objectExtraction.creator.strategyInit;


import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StrategyInitCreatorTest {

    private static final String NAME = "StrategyInitCreator";
    private static final int PRIORITY = 1;

//    private static final RawMessageFactory<String> messageFactory = new BotRawMessageFactory();
//
//    @Autowired
//    private StrategyInitCreator creator;
//
//    @Test
//    void shouldCheckCreationAttemptWhenDatumIsNull() {
//        RawMessage<String> expectedMessage = messageFactory.create("datum.isNull").add(NAME);
//        Result<Integer> result = creator.create(null);
//        assertThat(result.getSuccess()).isFalse();
//        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
//    }
//
//    @Test
//    void shouldCheckCreationAttemptWhenPriorityIsNull() {
//        RawMessage<String> expectedMessage = messageFactory.create("datum.priority.isNull").add(NAME);
//        Result<Integer> result = creator.create(new StrategyInitDatum());
//        assertThat(result.getSuccess()).isFalse();
//        assertThat(result.getRawMessage()).isEqualTo(expectedMessage);
//    }
//
//    @Test
//    void shouldCheckCreation() {
//        StrategyInitDatum datum = new StrategyInitDatum();
//        datum.setPriority(PRIORITY);
//        Result<Integer> result = creator.create(datum);
//        assertThat(result.getSuccess()).isTrue();
//        assertThat(result.getValue()).isEqualTo(PRIORITY);
//    }
}
