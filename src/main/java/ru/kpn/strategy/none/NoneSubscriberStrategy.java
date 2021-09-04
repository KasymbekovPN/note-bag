package ru.kpn.strategy.none;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.calculator.strategy.StrategyResultCalculator;
import ru.kpn.i18n.I18n;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategy.Matcher;

import java.util.Optional;

@Component
public class NoneSubscriberStrategy extends BaseSubscriberStrategy {

    @Value("${telegram.tube.strategies.noneSubscriberStrategy.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Autowired
    public void setI18n(I18n i18n) {
        this.i18n = i18n;
    }

    @Autowired
    public void setResultCalculator(StrategyResultCalculator<BotApiMethod<?>, String> resultCalculator) {
        this.resultCalculator = resultCalculator;
    }

    @Autowired
    @Qualifier("alwaysTrueStrategyMatcher")
    public void setMatcher(Matcher matcher){
        this.matcher = matcher;
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        return Optional.of(resultCalculator.calculate(calculateChatId(value), calculateMessage(value)));
    }

    private String calculateMessage(Update value) {
        return i18n.get("noneSubscriberStrategy.unknownInput", value.getMessage().getText());
    }
}
