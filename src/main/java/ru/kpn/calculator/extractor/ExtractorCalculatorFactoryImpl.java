package ru.kpn.calculator.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculatorOLd;
import ru.kpn.extractor.IterableExtractor;
import ru.kpn.i18n.builder.MessageBuilderFactory;
import ru.kpn.subscriber.Subscriber;

@Service
public class ExtractorCalculatorFactoryImpl implements ExtractorCalculatorFactory<Update, BotApiMethod<?>> {

    @Autowired
    private MessageBuilderFactory messageBuilderFactory;

    @Autowired
    private StrategyResultCalculatorOLd<BotApiMethod<?>, String> resultCalculator;

    @Override
    public ExtractorCalculator<Update, BotApiMethod<?>> create(Subscriber<Update, BotApiMethod<?>> subscriber) {
        return new SimpleExtractorCalculator(
                new IterableExtractor(subscriber),
                new SimpleExtractorCalculator.DefaultAnswerGeneratorImpl(messageBuilderFactory, resultCalculator)
        );
    }
}
