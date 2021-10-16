package ru.kpn.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;
import utils.UpdateInstanceBuilder;

import java.util.Optional;
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
    void shouldCheckPrioritySettingAndGetting() {
        int expectedPriority = RANDOM.nextInt();
        strategy.setPriority(expectedPriority);
        assertThat(expectedPriority).isEqualTo(strategy.getPriority());
    }

    @RepeatedTest(100)
    void shouldCheckChatIdCalculation(){
        Long expectedChatId = ((Integer) RANDOM.nextInt()).longValue();
        Update update = new UpdateInstanceBuilder().chatId(expectedChatId).build();
        assertThat(expectedChatId.toString()).isEqualTo(strategy.calculateChatId(update));
    }

    @Test
    void shouldCheckMethodCheckAndGetMessageWithoutMessage() {
        Update update = new Update();
        assertThat(strategy.checkAndGetMessage(update)).isEmpty();
    }

    @Test
    void shouldCheckMethodCheckAndGetMessage() {
        Update update = new UpdateInstanceBuilder().build();
        assertThat(strategy.checkAndGetMessage(update)).isPresent();
    }

    @RepeatedTest(100)
    void shouldCheckNullMatcher() {
        String template = String.valueOf(RANDOM.nextInt());
        assertThat(strategy.matchTemplate(template)).isFalse();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckMatcher.csv")
    void shouldCheckMatcher(String template, Boolean expectedResult) {
        strategy.setMatcher(new TestMatcher());
        assertThat(strategy.matchTemplate(template)).isEqualTo(expectedResult);
    }

    private static class TestSubscriberStrategy extends BaseSubscriberStrategy{
        @Override
        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public String calculateChatId(Update value) {
            return super.calculateChatId(value);
        }

        public Optional<Message> checkAndGetMessage(Update value) {
            if (value.hasMessage()){
                Message message = value.getMessage();
                if (message.getChat() != null && message.getChatId() != null &&
                        message.getFrom() != null && message.getText() != null){
                    return Optional.of(message);
                }
            }
            return Optional.empty();
        }

        @Override
        public void setMatcher(Function<String, Boolean> matcher) {
            this.matcher = matcher;
        }

        public boolean matchTemplate(String text) {
            return matcher != null && matcher.apply(text);
        }
    }

    private static class TestMatcher implements Function<String, Boolean>{
        @Override
        public Boolean apply(String s) {
            return s.equals("true");
        }
    }
}
