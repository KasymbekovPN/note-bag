package ru.kpn.strategy.none;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.i18n.builder.MessageBuilderFactory;
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
    @Qualifier("alwaysTrueStrategyMatcher")
    public void setMatcher(Matcher matcher){
        this.matcher = matcher;
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        return Optional.of(resultCalculator.calculate(calculateChatId(value), calculateMessage(value)));
    }

    private String calculateMessage(Update value) {
        return messageBuilderFactory.create("noneSubscriberStrategy.unknownInput")
                .arg(value.getMessage().getText())
                .build();
    }
}
