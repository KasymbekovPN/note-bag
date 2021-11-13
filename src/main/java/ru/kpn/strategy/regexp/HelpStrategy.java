package ru.kpn.strategy.regexp;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.rawMessage.RawMessage;

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
    public RawMessage<String> runAndGetRawMessage(Update value) {
        return createRawMessage("strategy.message.help").add(calculateChatId(value));
    }
}
