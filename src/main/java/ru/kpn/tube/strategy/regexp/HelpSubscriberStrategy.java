package ru.kpn.tube.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.kpn.model.telegram.TubeMessage;
import ru.kpn.tube.matcher.MatcherType;
import ru.kpn.tube.matcher.SubscriberStrategyMatcherFactory;
import ru.kpn.tube.strategy.BaseSubscriberStrategy;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class HelpSubscriberStrategy extends BaseSubscriberStrategy {

    @Value("${telegram.tube.strategies.helpSubscriberStrategy.priority}")
    private Integer priority;
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.text}")
    private String text;
    @Value("${telegram.tube.strategies.helpSubscriberStrategy.template}")
    private String template;

    @Autowired
    private SubscriberStrategyMatcherFactory factory;

    // TODO: 18.08.2021 there is need another way of setting of matcher
    @PostConstruct
    private void init(){
        matcher = factory.create(MatcherType.REGEX, template);
    }

    @Override
    protected Optional<BotApiMethod<?>> executeImpl(TubeMessage value) {
        return Optional.of(new SendMessage(value.getChatId(), text));
    }

    @Override
    public Integer getPriority() {
        return priority;
    }
}
