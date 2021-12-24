package ru.kpn.strategy.strategies.regexp;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.seed.Seed;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;

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
    public Seed<String> runAndGetRawMessage(Update value) {
        return builder().code("strategy.message.help").arg(calculateChatId(value)).build();
    }
}
