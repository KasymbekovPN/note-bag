package ru.kpn.tube.strategy.regexp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.matcher.RegExpSubscriberStrategyMatcher;
import ru.kpn.tube.strategy.SubscriberStrategy;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class HelpSubscriberStrategyTest {

    private static final String CHAT_ID = "123";

    private SubscriberStrategy<TubeMessage, BotApiMethod<?>> strategy;
    private TubeMessage.TubeMessageBuilder builder;

    @BeforeEach
    void setUp() {
        strategy = new HelpSubscriberStrategy(new RegExpSubscriberStrategyMatcher("/help"));
        builder = TubeMessage.builder()
                .nullState(false)
                .chatId(CHAT_ID);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "helpSubscriberStrategyTest.csv", lineSeparator = ";")
    void shouldCheckText(String command, boolean isPresent, String expectedText) {
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
}
