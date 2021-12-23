package ru.kpn.strategy.calculaters.answerCalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.i18n.builder.MessageBuilder;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.rawMessage.RawMessageOld;
import utils.UpdateInstanceBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class AnswerCalculatorTest {

    private static final Long CHAT_ID = 123L;
    private static final String TEMPLATE = "??? : %s";
    private static final String TEXT = "some text";
    private static final String CODE = "noneSubscriberStrategy.unknownInput";

    private AnswerCalculator answerCalculator;

    @BeforeEach
    void setUp() {
        answerCalculator = new AnswerCalculator(new TestMessageBuilderFactory());
    }

    @Test
    void shouldCheckCalculation() {
        Update update = createUpdate();
        SendMessage expectedCalcResult = createExpectedCalcResult(update);
        SendMessage calcResult = (SendMessage) answerCalculator.apply(new TextRawMessageOld());
        assertThat(expectedCalcResult.getChatId()).isEqualTo(calcResult.getChatId());
        assertThat(expectedCalcResult.getText()).isEqualTo(calcResult.getText());
    }

    private static class TextRawMessageOld implements RawMessageOld<String> {

        @Override
        public String getCode() {
            return CODE;
        }

        @Override
        public RawMessageOld<String> add(Object o) {return this;}

        @Override
        public Object[] getArgs() {
            return new Object[]{
                    String.valueOf(CHAT_ID),
                    TEXT
            };
        }

        @Override
        public RawMessageOld<String> setCode(String code) {
            return null;
        }
    }

    private SendMessage createExpectedCalcResult(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String text = String.format(TEMPLATE, TEXT);
        return new SendMessage(chatId, text);
    }

    private Update createUpdate() {
        return new UpdateInstanceBuilder()
                .chatId(CHAT_ID)
                .text(TEXT)
                .build();
    }

    private static class TestMessageBuilderFactory implements MessageBuilderFactory {
        @Override
        public MessageBuilder create(String code) {
            return new TestMessageBuilder();
        }
    }

    private static class TestMessageBuilder implements MessageBuilder {

        private String arg;

        @Override
        public MessageBuilder arg(Object object) {
            arg = String.valueOf(object);
            return this;
        }

        @Override
        public String build() {
            return String.format(TEMPLATE, arg);
        }
    }
}