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
import ru.kpn.rawMessage.RawMessage;

import java.util.Optional;
import java.util.function.Function;

@Component
public class GetCurrentBufferDatumStrategy extends BaseSubscriberStrategy {

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
        String chatId = calculateChatId(value);
        Optional<BufferDatum<BufferDatumType, String>> maybeDatum = extractDatum(value);

        RawMessage<String> rawMessage = createRawMessage(
                maybeDatum.isPresent()
                        ? "strategy.message.getCurrentBufferDatum.exist"
                        : "strategy.message.getCurrentBufferDatum.notExist"
        )
                .add(chatId)
                .add(chatId);

        return maybeDatum.isPresent() ? rawMessage.add(maybeDatum.get().getContent()) : rawMessage;
    }

    private Optional<BufferDatum<BufferDatumType, String>> extractDatum(Update value) {
        return botBuffer.peek(value.getMessage().getChatId());
    }
}
