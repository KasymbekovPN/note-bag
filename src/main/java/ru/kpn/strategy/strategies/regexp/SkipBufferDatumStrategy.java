package ru.kpn.strategy.strategies.regexp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.buffer.Buffer;
import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;
import ru.kpn.injection.Inject;
import ru.kpn.injection.InjectionType;
import ru.kpn.seed.Seed;
import ru.kpn.seed.SeedBuilder;
import ru.kpn.strategy.strategies.BaseSubscriberStrategy;

import java.util.function.Function;

@Component
public class SkipBufferDatumStrategy extends BaseSubscriberStrategy {

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
    public Seed<String> runAndGetRawMessage(Update value) {
        Long id = value.getMessage().getChatId();
        int bufferSize = botBuffer.getSize(id);

        final SeedBuilder<String> builder = builder().code(
                        bufferSize == 0
                                ? "strategy.message.skipBufferDatum.isEmpty"
                                : "strategy.message.skipBufferDatum.isNotEmpty"
                )
                .arg(calculateChatId(value));
        if (bufferSize != 0){
            builder.arg(bufferSize - 1);
        }
        return builder.build();
    }
}
