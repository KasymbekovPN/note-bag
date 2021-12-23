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
public class SimpleNoteStrategy extends BaseSubscriberStrategy {

    private Function<Update, String> extractor;

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Inject(InjectionType.PRIORITY)
    public void setPriority(Integer priority){
        this.priority = priority;
    }

    @Inject(InjectionType.EXTRACTOR)
    public void setExtractor(Function<Update, String> extractor){
        this.extractor = extractor;
    }

    @Inject(InjectionType.MATCHER)
    public void setMatcher(Function<Update, Boolean> matcher){
        this.matcher = matcher;
    }

    @Override
    public RawMessageOld<String> runAndGetRawMessage(Update value) {
        putIntoBuffer(value);
        return getAnswer(value);
    }

    private void putIntoBuffer(Update value) {
        Long chatId = value.getMessage().getChatId();
        String text = extractor.apply(value);
        final BufferDatum<BufferDatumType, String> datum = botBuffer.createDatum(BufferDatumType.SIMPLE_TEXT, text);
        botBuffer.add(chatId, datum);
    }

    private RawMessageOld<String> getAnswer(Update value) {
        return createRawMessage("strategy.message.simpleNode").add(calculateChatId(value));
    }
}
