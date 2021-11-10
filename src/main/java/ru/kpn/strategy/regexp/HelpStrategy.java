package ru.kpn.strategy.regexp;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.RawMessage;

import java.util.function.Function;

@Component
public class HelpStrategy extends BaseSubscriberStrategy {

    @Inject(InjectionType.PRIORITY)
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Inject(InjectionType.MATCHER)
    public void setMatcher(Function<Update, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    public RawMessage<String> runAndGetAnswer(Update value) {
        RawMessage<String> source = createSource("strategy.message.help");
        source.add(calculateChatId(value));

        return source;
    }
}
