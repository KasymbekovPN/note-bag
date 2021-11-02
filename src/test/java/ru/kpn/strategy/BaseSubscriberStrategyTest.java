package ru.kpn.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;
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

    @RepeatedTest(100)
    void shouldCheckNullMatcher() {
        String text = String.valueOf(RANDOM.nextInt());
        assertThat(strategy.matchTemplate(new UpdateInstanceBuilder().text(text).build())).isFalse();
    }

    // TODO: 02.11.2021 restore
//    @ParameterizedTest
//    @CsvFileSource(resources = "shouldCheckMatcher.csv")
//    void shouldCheckMatcher(String template, Boolean expectedResult) {
//        strategy.setMatcherOld(new TestMatcher());
//        Update update = new UpdateInstanceBuilder().text(template).build();
//        assertThat(strategy.matchTemplate(update)).isEqualTo(expectedResult);
//    }

    private static class TestSubscriberStrategy extends BaseSubscriberStrategy{
        @Override
        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public String calculateChatId(Update value) {
            return super.calculateChatId(value);
        }

        // TODO: 02.11.2021 restore 
//        @Override
//        public void setMatcherOld(Function<Update, Boolean> matcher) {
//            this.matcher = matcher;
//        }

        public boolean matchTemplate(Update text) {
            return matcher != null && matcher.apply(text);
        }

        @Override
        public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
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
