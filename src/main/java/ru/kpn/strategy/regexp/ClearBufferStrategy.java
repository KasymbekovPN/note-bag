package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.RawMessage;
 
@Component
public class ClearBufferStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Value("${telegram.tube.strategies.clearBuffer.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public RawMessage<String> runAndGetAnswer(Update value) {
        botBuffer.clear(value.getMessage().getChatId());
        return calculateAnswer(value);
    }

    private RawMessage<String> calculateAnswer(Update value) {
        RawMessage<String> source = createSource("strategy.message.clearBuffer.isCleaned");
        source.add(calculateChatId(value));

        return source;
    }
}
