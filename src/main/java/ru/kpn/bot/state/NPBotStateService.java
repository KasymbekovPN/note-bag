package ru.kpn.bot.state;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// TODO: 06.10.2021 decide need or not 
@Service
public class NPBotStateService implements BotStateService<User, NPBotState> {

    private static final NPBotState DEFAULT_STATE = NPBotState.UNKNOWN;

    private final Map<Long, NPBotState> states = new ConcurrentHashMap<>();

    @Override
    public NPBotState get(User user) {
        return states.getOrDefault(user.getId(), DEFAULT_STATE);
    }

    @Override
    public void set(User user, NPBotState state) {
        states.put(user.getId(), state);
    }

    @Override
    public void remove(User user) {
        states.remove(user.getId());
    }
}
