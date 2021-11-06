package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.function.Function;

@Component
public class SimpleNoteStrategy extends BaseSubscriberStrategy {

    // TODO: 06.11.2021 del
    @Autowired
    @Qualifier("simpleNoteExtractorOld")
    public Function<Update, String> extractor;

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Value("${telegram.tube.strategies.simpleNote.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        putIntoBuffer(value);
        return getAnswer(value);
    }

    private void putIntoBuffer(Update value) {
        Long chatId = value.getMessage().getChatId();
        String text = extractor.apply(value);
        final BufferDatum<BufferDatumType, String> datum = botBuffer.createDatum(BufferDatumType.SIMPLE_TEXT, text);
        botBuffer.add(chatId, datum);
    }

    private StrategyCalculatorSource<String> getAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.simpleNode");
        source.add(calculateChatId(value));

        return source;
    }
}
