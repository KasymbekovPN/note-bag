package ru.kpn.model.telegram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.User;

@Builder
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class TubeMessage {
    private final Boolean nullState;
    private final Long chatId;
    private final String text;
    private final User from;
}
