package ru.kpn.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.RawMessage;
import utils.UpdateInstanceBuilder;

import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseSubscriberStrategyTest {

    private static final Random RANDOM = new Random();

    private TestSubscriberStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new TestSubscriberStrategy();
    }

    @RepeatedTest(100)
    void shouldCheckChatIdCalculation(){
        Long expectedChatId = ((Integer) RANDOM.nextInt()).longValue();
        Update update = new UpdateInstanceBuilder().chatId(expectedChatId).build();
        assertThat(expectedChatId.toString()).isEqualTo(strategy.calculateChatId(update));
    }

    @RepeatedTest(100)
    void shouldCheckNullMatcher() {
        String text = String.valueOf(RANDOM.nextInt());
        assertThat(strategy.matchTemplate(new UpdateInstanceBuilder().text(text).build())).isFalse();
    }

    private static class TestSubscriberStrategy extends BaseSubscriberStrategy{

        public String calculateChatId(Update value) {
            return super.calculateChatId(value);
        }

        public boolean matchTemplate(Update text) {
            return matcher != null && matcher.apply(text);
        }

        @Override
        public RawMessage<String> runAndGetAnswer(Update value) {
            return null;
        }
    }

    private static class TestMatcher implements Function<Update, Boolean>{
        @Override
        public Boolean apply(Update s) {
            return s.getMessage().getText().equals("true");
        }
    }
}
