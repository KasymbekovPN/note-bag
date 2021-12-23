package ru.kpn.strategy.strategies.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;
import ru.kpn.statusSeed.RawMessageOld;

import java.util.function.Function;

@Component
public class SkipBufferDatumStrategy extends BaseSubscriberStrategy {

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
    public RawMessageOld<String> runAndGetRawMessage(Update value) {
        Long id = value.getMessage().getChatId();
        int bufferSize = botBuffer.getSize(id);
        RawMessageOld<String> rawMessageOld = createRawMessage(
                bufferSize == 0
                        ? "strategy.message.skipBufferDatum.isEmpty"
                        : "strategy.message.skipBufferDatum.isNotEmpty"
        )
                .add(calculateChatId(value));
        return bufferSize != 0 ? rawMessageOld.add(bufferSize - 1) : rawMessageOld;
    }
}
