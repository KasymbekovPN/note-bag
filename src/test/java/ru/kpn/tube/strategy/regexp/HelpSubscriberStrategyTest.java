package ru.kpn.tube.strategy.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelpSubscriberStrategyTest {

    private static final String CHAT_ID = "123";

    @Autowired
    private HelpSubscriberStrategy strategy;

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    private Integer expectedPriority;
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.text}")
    private String expectedText;

    private TubeMessage.TubeMessageBuilder builder;

    @BeforeEach
    void setUp() {
        builder = TubeMessage.builder()
                .nullState(false)
                .chatId(CHAT_ID);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "helpSubscriberStrategyTest.csv")
    void shouldCheckText(String command, boolean isPresent) {
        TubeMessage tm = builder.text(command).build();
        Optional<BotApiMethod<?>> maybeMethod = strategy.execute(tm);
        boolean maybeMethodPresent = maybeMethod.isPresent();
        assertThat(maybeMethodPresent).isEqualTo(isPresent);
        if (maybeMethodPresent){
            SendMessage sm = (SendMessage) maybeMethod.get();
            assertThat(sm.getChatId()).isEqualTo(CHAT_ID);
            assertThat(sm.getText()).isEqualTo(expectedText);
        }
    }

    @Test
    void shouldCheckGetPriority() {
        assertThat(expectedPriority).isEqualTo(strategy.getPriority());
    }
}
