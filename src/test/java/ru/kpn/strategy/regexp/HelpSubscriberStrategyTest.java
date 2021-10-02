package ru.kpn.strategy.regexp;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.BotStrategyCalculatorSource;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpSubscriberStrategyTest {

    private static final Long CHAT_ID = 123L;
    private static final String COMMAND = "/help";

    @Autowired
    private HelpSubscriberStrategy strategy;

    private UpdateInstanceBuilder builder;
    private Decoder decoder;

    @BeforeEach
    void setUp() {
        builder = new UpdateInstanceBuilder().chatId(CHAT_ID);
        decoder = new Decoder(strategy);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckStrategyExecution_help.csv")
    void shouldCheckStrategyExecution(String command, boolean isPresent) {
        Update update = builder.text(command).build();
        assertThat(strategy.execute(update).isPresent()).isEqualTo(isPresent);
    }

    @Test
    void shouldCheckAnswer() {
        BotStrategyCalculatorSource expectedSource = new BotStrategyCalculatorSource("strategy.message.help");
        expectedSource.add(String.valueOf(CHAT_ID));

        StrategyCalculatorSource<String> source = decoder.getSource(builder.text(COMMAND).build());
        assertThat(expectedSource).isEqualTo(source);
    }

    @AllArgsConstructor
    private static class Decoder extends HelpSubscriberStrategy{
        private final HelpSubscriberStrategy strategy;

        @Override
        protected StrategyCalculatorSource<String> getSource(Update value) {
            return strategy.getSource(value);
        }
    }
}
