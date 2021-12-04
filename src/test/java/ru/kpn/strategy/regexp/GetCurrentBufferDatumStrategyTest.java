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
//public class GetCurrentBufferDatumStrategyTest {
//
//    private static final Long ID = 123L;
//    private static final String COMMAND = "/get current buffer datum";
//    private static final String TEXT = "some text";
//
//    @Autowired
//    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;
//    @Autowired
//    private StrategyInitCreatorOld strategyInitCreatorOld;
//    @Autowired
//    private GetCurrentBufferDatumStrategy strategy;
//    @Autowired
//    private RawMessageFactory<String> rawMessageFactory;
//
//    private UpdateInstanceBuilder builder;
//    private RawMessage<String> ifExistAnswer;
//    private RawMessage<String> ifNotExistAnswer;
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
//        String sid = String.valueOf(ID);
//        ifExistAnswer = rawMessageFactory.create("strategy.message.getCurrentBufferDatum.exist")
//                .add(sid)
//                .add(sid)
//                .add(TEXT);
//
//        ifNotExistAnswer = rawMessageFactory.create("strategy.message.getCurrentBufferDatum.notExist")
//                .add(sid)
//                .add(sid);
//    }
//
//    @ParameterizedTest
//    @CsvFileSource(resources = "shouldCheckStrategyExecution_getCurrentBufferDatum.csv")
//    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
//        Update update = builder.text(command).build();
//        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
//    }
//
//    @Test
//    void shouldCheckAnswerIfCurrentDatumNotExist() {
//        RawMessage<String> answer = strategy.runAndGetRawMessage(builder.build());
//        assertThat(ifNotExistAnswer).isEqualTo(answer);
//    }
//
//    @Test
//    void shouldCheckAnswerIfCurrentDatumExist() {
//        botBuffer.add(ID, new TestBufferDatum(TEXT));
//        RawMessage<String> answer = strategy.runAndGetRawMessage(builder.build());
//        assertThat(ifExistAnswer).isEqualTo(answer);
//    }
//
//    @Test
//    void shouldCheckPriority() {
//        assertThat(strategy.getPriority()).isEqualTo(strategyInitCreatorOld.getDatum("getCurrentBufferDatum").getPriority());
//    }
//
//    @AfterEach
//    void tearDown() {
//        botBuffer.clear(ID);
//    }
//}
