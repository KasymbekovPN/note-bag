package ru.kpn.strategy.strategies.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.seed.Seed;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;

import java.util.function.Function;

@Component
public class LinkStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

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
        putIntoBuffer(value);
        return getAnswer(value);
    }

    private void putIntoBuffer(Update value) {
        BufferDatum<BufferDatumType, String> datum = botBuffer.createDatum(BufferDatumType.LINK, value.getMessage().getText());
        botBuffer.add(value.getMessage().getChatId(), datum);
    }

    private Seed<String> getAnswer(Update value) {
        return builder().code("strategy.message.link")
                .arg(calculateChatId(value))
                .arg(value.getMessage().getText())
                .build();
    }
}
