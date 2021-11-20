package ru.kpn.rawMessage;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
class BotRawMessage implements RawMessage<String> {
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
