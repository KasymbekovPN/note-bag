package ru.kpn.strategy.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.strategy.BaseSubscriberStrategy;

import java.util.function.Function;

@Component
public class GetBufferStatusStrategy extends BaseSubscriberStrategy {

    @Autowired
    private Buffer<Long, BufferDatum<BufferDatumType, String>> botBuffer;

    @Value("${telegram.tube.strategies.getBufferStatusStrategy.priority}")
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Autowired
    @Qualifier("getBufferStatusStrategyMatcher")
    public void setMatcher(Function<String, Boolean> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected BotApiMethod<?> executeImpl(Update value) {
        int bufferSize = getBufferSize(value);
        String code = getCode(bufferSize);
        Object[] args = getArgs(value, bufferSize);
        return strategyCalculator.calculate(code, args);
    }

    private String getCode(int bufferSize) {
        return bufferSize == 0 ? "strategy.message.getBufferStatus.empty" : "strategy.message.getBufferStatus.contains";
    }

    private Object[] getArgs(Update value, int bufferSize) {
        if (bufferSize == 0){
            return new Object[]{
                    calculateChatId(value),
                    calculateChatId(value)
            };
        }
        return new Object[]{
                calculateChatId(value),
                calculateChatId(value),
                bufferSize
        };
    }

    private int getBufferSize(Update value) {
        Long chatId = value.getMessage().getChatId();
        return botBuffer.getSize(chatId);
    }
}
