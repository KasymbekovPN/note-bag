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
public class ClearBufferStrategyTest {

    private static final Long ID = 123L;
    private static final String COMMAND = "/clr buffer";

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Autowired
    private ClearBufferStrategy strategy;

    private UpdateInstanceBuilder builder;
    private BotStrategyCalculatorSource expectedAnswer;
    private Decorator decorator;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(ID);

        builder = new UpdateInstanceBuilder()
                .chatId(ID)
                .from(user)
                .text(COMMAND);

        expectedAnswer = new BotStrategyCalculatorSource("strategy.message.clearBuffer.isCleaned");
        expectedAnswer.add(String.valueOf(ID));

        decorator = new Decorator(strategy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_clearBuffer.csv")
    void shouldCheckStrategyExecution(String command, Boolean expectedIsPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(expectedIsPresent);
    }

    @Test
    void shouldCheckAnswer() {
        StrategyCalculatorSource<String> source = decorator.getSource(builder.build());
        assertThat(expectedAnswer).isEqualTo(source);
    }

    @Test
    void shouldCheckClearing() {
        botBuffer.add(ID, new TestBufferDatum());
        decorator.getSource(builder.build());
        int size = botBuffer.getSize(ID);
        assertThat(size).isZero();
    }

    @AllArgsConstructor
    private static class Decorator extends ClearBufferStrategy{
        private ClearBufferStrategy strategy;

        @Override
        protected StrategyCalculatorSource<String> getSource(Update value) {
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
