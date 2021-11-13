package ru.kpn.rawMessage;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// TODO: 13.11.2021 it must be package-private 
@ToString // TODO: 10.11.2021 del 
@EqualsAndHashCode
@RequiredArgsConstructor
public class BotRawMessage implements RawMessage<String> {
    private final String code;
    private final List<Object> args = new ArrayList<>();

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public RawMessage<String> add(Object o) {
        args.add(o);
        return this;
    }

    @Override
    public Object[] getArgs() {
        return args.toArray();
    }
}
