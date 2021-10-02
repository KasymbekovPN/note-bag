package ru.kpn.strategy.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.strategyCalculator.BotStrategyCalculatorSource;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GetBufferStatusStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String COMMAND = "/get buffer status";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Autowired
    private GetBufferStatusStrategy strategy;

    private UpdateInstanceBuilder builder;
    private BotStrategyCalculatorSource ifEmptyAnswer;
    private BotStrategyCalculatorSource ifNotEmptyAnswer;
    private Decorator decorator;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(CHAT_ID);

        builder = new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .from(user)
                .text(COMMAND);

        ifEmptyAnswer = new BotStrategyCalculatorSource("strategy.message.getBufferStatus.empty");
        ifEmptyAnswer.add(String.valueOf(CHAT_ID));
        ifEmptyAnswer.add(String.valueOf(CHAT_ID));

        ifNotEmptyAnswer = new BotStrategyCalculatorSource("strategy.message.getBufferStatus.contains");
        ifNotEmptyAnswer.add(String.valueOf(CHAT_ID));
        ifNotEmptyAnswer.add(String.valueOf(CHAT_ID));
        ifNotEmptyAnswer.add(1);

        decorator = new Decorator(strategy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_getBufferStatus.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswerIfBufferEmpty() {
        StrategyCalculatorSource<String> source = decorator.getSource(builder.build());
        assertThat(ifEmptyAnswer).isEqualTo(source);
    }

    @Test
    void shouldCheckAnswerIfBufferNotEmpty() {
        botBuffer.add(CHAT_ID, new TestBufferDatum());
        StrategyCalculatorSource<String> source = decorator.getSource(builder.build());
        assertThat(ifNotEmptyAnswer).isEqualTo(source);
    }

    private static class Decorator extends GetBufferStatusStrategy{

        private final GetBufferStatusStrategy strategy;

        public Decorator(GetBufferStatusStrategy strategy) {
            this.strategy = strategy;
        }

        @Override
        public StrategyCalculatorSource<String> getSource(Update value) {
            return strategy.getSource(value);
        }
    }

    private static class TestBufferDatum implements BufferDatum<BufferDatumType, String>{
        @Override
        public BufferDatumType getType() {
            return null;
        }

        @Override
        public String getContent() {
            return null;
        }
    }
}
