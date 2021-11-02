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

// TODO: 02.11.2021 rename: name without 'Strategy' suffix
@Component
public class LinkStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Value("${telegram.tube.strategies.link.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Autowired
    @Qualifier("linkMatcherOld")
    public void setMatcherOld(Function<Update, Boolean> matcher) {
        this.matcher = matcher;
    }

    @Override
    public StrategyCalculatorSource<String> runAndGetAnswer(Update value) {
        putIntoBuffer(value);
        return getAnswer(value);
    }

    private void putIntoBuffer(Update value) {
        BufferDatum<BufferDatumType, String> datum = botBuffer.createDatum(BufferDatumType.LINK, value.getMessage().getText());
        botBuffer.add(value.getMessage().getChatId(), datum);
    }

    private StrategyCalculatorSource<String> getAnswer(Update value) {
        StrategyCalculatorSource<String> source = createSource("strategy.message.link");
        source.add(calculateChatId(value));
        source.add(value.getMessage().getText());

        return source;
    }
}
