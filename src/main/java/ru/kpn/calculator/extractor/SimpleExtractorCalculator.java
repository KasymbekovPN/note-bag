package ru.kpn.calculator.extractor;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculatorOLd;
import ru.kpn.extractor.SubscriberExtractor;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.subscriber.Subscriber;

import java.util.Optional;

class SimpleExtractorCalculator implements ExtractorCalculator<Update, BotApiMethod<?>> {

    private final SubscriberExtractor<Update, BotApiMethod<?>> extractor;
    private final AnswerGenerator defaultAnswerGenerator;

    public SimpleExtractorCalculator(SubscriberExtractor<Update, BotApiMethod<?>> extractor, AnswerGenerator defaultAnswerGenerator) {
        this.extractor = extractor;
        this.defaultAnswerGenerator = defaultAnswerGenerator;
    }

    @Override
    public BotApiMethod<?> calculate(Update message) {
        Optional<Subscriber<Update, BotApiMethod<?>>> maybe;
        do {
            maybe = extractor.getNext();
            if (maybe.isPresent()){
                Optional<BotApiMethod<?>> executionResult = maybe.get().executeStrategy(message);
                if (executionResult.isPresent()){
                    return executionResult.get();
                }
            }
        } while (maybe.isPresent());

        return defaultAnswerGenerator.generate(message);
    }

    interface AnswerGenerator {
        BotApiMethod<?> generate(Update update);
    }

    @AllArgsConstructor
    static class DefaultAnswerGeneratorImpl implements AnswerGenerator{

        private final MessageBuilderFactory messageBuilderFactory;
        private final StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator;

        @Override
        public BotApiMethod<?> generate(Update update) {
            String chatId = update.getMessage().getChatId().toString();
            String message = messageBuilderFactory.create("noneSubscriberStrategy.unknownInput")
                    .arg(update.getMessage().getText())
                    .build();
            return resultCalculator.calculate(chatId, message);
        }
    }
}
