package ru.kpn.model.telegtam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.User;

@Builder
@Getter
@AllArgsConstructor
public class TubeMessage {
    private final Boolean nullState;
    private final Long chatId;
    private final String text;
    private final User user;
}
