// TODO: 04.12.2021 restore
//package ru.kpn.strategy.regexp;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.User;
//import ru.kpn.buffer.Buffer;
//import ru.kpn.buffer.BufferDatum;
//import ru.kpn.buffer.BufferDatumType;
//import ru.kpn.creator.StrategyInitCreatorOld;
//import ru.kpn.rawMessage.RawMessage;
//import ru.kpn.rawMessage.RawMessageFactory;
//import utils.TestBufferDatum;
//import utils.UpdateInstanceBuilder;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class SkipBufferDatumStrategyTest {
//
//    private static final Long ID = 123L;
//    private static final String COMMAND = "/skip buffer datum";
//
//    @Autowired
//    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
//    @Autowired
//    private SkipBufferDatumStrategy strategy;
//    @Autowired
//    private StrategyInitCreatorOld strategyInitCreatorOld;
//    @Autowired
//    private RawMessageFactory<String> rawMessageFactory;
//
//    private UpdateInstanceBuilder builder;
//    private RawMessage<String> ifEmptyRawMessage;
//    private RawMessage<String> ifNotEmptyRawMessage;
//
//    @BeforeEach
//    void setUp() {
//        User user = new User();
//        user.setId(ID);
//
//        builder = new UpdateInstanceBuilder()
//                .chatId(ID)
//                .from(user)
//                .text(COMMAND);
//
//        ifEmptyRawMessage = rawMessageFactory.create("strategy.message.skipBufferDatum.isEmpty")
//                .add(String.valueOf(ID));
//
//        ifNotEmptyRawMessage = rawMessageFactory.create("strategy.message.skipBufferDatum.isNotEmpty")
//                .add(String.valueOf(ID))
//                .add(1);
//    }
//
//    @ParameterizedTest
//    @CsvFileSource(resources = "shouldCheckStrategyExecution_skipBufferDatum.csv")
//    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
//        Update update = builder.text(command).build();
//        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
//    }
//
//    @Test
//    void shouldCheckAnswerIfBufferEmpty() {
//        RawMessage<String> answer = strategy.runAndGetRawMessage(builder.build());
//        assertThat(ifEmptyRawMessage).isEqualTo(answer);
//    }
//
//    @Test
//    void shouldCheckAnswerIfBufferNotEmpty() {
//        botBuffer.add(ID, new TestBufferDatum("1"));
//        botBuffer.add(ID, new TestBufferDatum("2"));
//        RawMessage<String> answer = strategy.runAndGetRawMessage(builder.build());
//        assertThat(ifNotEmptyRawMessage).isEqualTo(answer);
//    }
//
//    @Test
//    void shouldCheckPriority() {
//        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreatorOld.getDatum("skipBufferDatum").getPriority());
//    }
//
//    @AfterEach
//    void tearDown() {
//        botBuffer.clear(ID);
//    }
//}
