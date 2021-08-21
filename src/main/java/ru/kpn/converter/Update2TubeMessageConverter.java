package ru.kpn.converter;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.model.telegram.TubeMessage;

import java.util.Optional;
import java.util.function.Function;

@Component
public class Update2TubeMessageConverter implements Function<Update, TubeMessage> {
    @Override
    public TubeMessage apply(Update update) {
        TubeMessage.TubeMessageBuilder builder = TubeMessage.builder();
        Optional<Message> maybeMessage = checkAndGetMessage(update);
        if (maybeMessage.isPresent()){
            Message message = maybeMessage.get();
            builder
                    .nullState(false)
                    .text(message.getText())
                    .chatId(message.getChatId().toString())
                    .from(message.getFrom());
        } else {
            builder.nullState(true);
        }
        return builder.build();
    }

    private Optional<Message> checkAndGetMessage(Update update) {
        if (update.hasMessage()){
            return checkMessage(update.getMessage());
        }
        return Optional.empty();
    }

    private Optional<Message> checkMessage(Message message) {
        return  message.getChat() != null && message.getChatId() != null &&
                message.getFrom() != null && message.getText() != null
                ? Optional.of(message)
                : Optional.empty();
    }
}
