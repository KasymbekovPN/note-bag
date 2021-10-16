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
public class SkipBufferDatumStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Value("${telegram.tube.strategies.skipBufferDatumStrategy.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Autowired
    @Qualifier("skipBufferDatumStrategyMatcher")
    public void setMatcher(Function<String, Boolean> matcher) {
        this.matcher = matcher;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        Long id = value.getMessage().getChatId();
        int bufferSize = botBuffer.getSize(id);
        StrategyCalculatorSource<String> source = createSource(bufferSize == 0
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
