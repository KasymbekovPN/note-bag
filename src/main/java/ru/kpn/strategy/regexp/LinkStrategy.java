package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.BotBufferDatum;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.strategy.BaseSubscriberStrategy;
import ru.kpn.strategyCalculator.StrategyCalculatorSource;

import java.util.function.Function;

@Component
public class LinkStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Value("${telegram.tube.strategies.linkStrategy.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Autowired
    @Qualifier("linkStrategyMatcher")
    public void setMatcher(Function<Update, Boolean> matcher) {
        this.matcher = matcher;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        putIntoBuffer(value);
        return getAnswer(value);
    }

    // TODO: 23.10.2021 create datum through botBuffer
    private void putIntoBuffer(Update value) {
        botBuffer.add(value.getMessage().getChatId(), new BotBufferDatum(BufferDatumType.LINK, value.getMessage().getText()));
    }

    private StrategyCalculatorSource<String> getAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.link");
        source.add(calculateChatId(value));
        source.add(value.getMessage().getText());

        return source;
    }
}
