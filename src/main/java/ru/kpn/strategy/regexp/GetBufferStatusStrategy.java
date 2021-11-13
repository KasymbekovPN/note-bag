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
import ru.kpn.rawMessage.RawMessage;

import java.util.function.Function;

@Component
public class GetBufferStatusStrategy extends BaseSubscriberStrategy {

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
    public RawMessage<String> runAndGetRawMessage(Update value) {
        int bufferSize = getBufferSize(value);
        String chatId = calculateChatId(value);

        RawMessage<String> rawMessage = createRawMessage(
                bufferSize == 0
                        ? "strategy.message.getBufferStatus.empty"
                        : "strategy.message.getBufferStatus.contains"
        )
                .add(chatId)
                .add(chatId);
        return bufferSize != 0 ? rawMessage.add(bufferSize) : rawMessage;
    }

    private int getBufferSize(Update value) {
        Long chatId = value.getMessage().getChatId();
        return botBuffer.getSize(chatId);
    }
}
