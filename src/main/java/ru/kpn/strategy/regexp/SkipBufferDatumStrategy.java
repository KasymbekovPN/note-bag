package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.RawMessage;

@Component
public class SkipBufferDatumStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Inject(InjectionType.PRIORITY)
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Override
    public RawMessage<String> runAndGetAnswer(Update value) {
        Long id = value.getMessage().getChatId();
        int bufferSize = botBuffer.getSize(id);
        RawMessage<String> source = createSource(bufferSize == 0
                ? "strategy.message.skipBufferDatum.isEmpty"
                : "strategy.message.skipBufferDatum.isNotEmpty"
        );
        source.add(calculateChatId(value));
        if (bufferSize > 0){
            source.add(bufferSize - 1);
        }

        return source;
    }
}
