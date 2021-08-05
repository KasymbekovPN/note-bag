package ru.kpn.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.model.telegram.TubeMessage;

import java.util.function.Function;

@Component
public class Update2TubeMessageConverter implements Function<Update, TubeMessage> {
    @Override
    public TubeMessage apply(Update update) {
        TubeMessage.TubeMessageBuilder builder = TubeMessage.builder();
        if (update.hasMessage()){
            Message message = update.getMessage();
            builder
                    .nullState(false)
                    .text(message.getText())
                    .chatId(message.getChatId())
                    .from(message.getFrom());
        } else {
            builder.nullState(true);
        }
        return builder.build();
    }
}
