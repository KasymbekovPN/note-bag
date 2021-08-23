package ru.kpn.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum NPBotState {
    UNKNOWN(-1),
    RESET(0);

    private final static Map<Integer, NPBotState> matchingById = new HashMap<>();

    static {
        for (NPBotState value : NPBotState.values()) {
            if (!value.equals(UNKNOWN)){
                matchingById.put(value.getId(), value);
            }
        }
    }

    private final int id;
}
