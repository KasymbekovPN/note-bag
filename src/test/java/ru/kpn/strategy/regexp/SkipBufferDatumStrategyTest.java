package ru.kpn.strategy.regexp;

import lombok.AllArgsConstructor;
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
public class SkipBufferDatumStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/skip buffer datum";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Autowired
    private SkipBufferDatumStrategy strategy;

    private UpdateInstanceBuilder builder;
    private BotStrategyCalculatorSource ifEmptyAnswer;
    private BotStrategyCalculatorSource ifNotEmptyAnswer;
    private Decorator decorator;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        ifEmptyAnswer = new BotStrategyCalculatorSource("strategy.message.skipBufferDatum.isEmpty");
        ifEmptyAnswer.add(String.valueOf(ID));

        ifNotEmptyAnswer = new BotStrategyCalculatorSource("strategy.message.skipBufferDatum.isNotEmpty");
        ifNotEmptyAnswer.add(String.valueOf(ID));
        ifNotEmptyAnswer.add(1);

        decorator = new Decorator(strategy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_skipBufferDatum.csv")
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
        botBuffer.add(ID, new TestBufferDatum());
        botBuffer.add(ID, new TestBufferDatum());
        StrategyCalculatorSource<String> source = decorator.getSource(builder.build());
        assertThat(ifNotEmptyAnswer).isEqualTo(source);
    }

    @AllArgsConstructor
    private static class Decorator extends SkipBufferDatumStrategy {

        private final SkipBufferDatumStrategy strategy;

        @Override
        public StrategyCalculatorSource<String> getSource(Update value) {
            return strategy.getSource(value);
        }
    }

    private static class TestBufferDatum implements BufferDatum<BufferDatumType, String> {
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
