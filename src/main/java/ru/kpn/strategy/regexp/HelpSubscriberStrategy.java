package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MatcherType;
import ru.kpn.matcher.SubscriberStrategyMatcherFactory;
import ru.kpn.strategy.BaseSubscriberStrategy;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class HelpSubscriberStrategy extends BaseSubscriberStrategy {

    // TODO: 04.09.2021 to base class 
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    private Integer priority;

    // TODO: 04.09.2021 translate it
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.text}")
    private String text;
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.template}")
    private String template;

    @Autowired
    private SubscriberStrategyMatcherFactory factory;

    // TODO: 04.09.2021 remove 
    @PostConstruct
    private void init(){
        matcher = factory.create(MatcherType.REGEX, template);
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(Update value) {
        return Optional.of(new SendMessage(calculateChatId(value), text));
    }

    // TODO: 04.09.2021 to base class
    @Override
    public Integer getPriority() {
        return priority;
    }
}
