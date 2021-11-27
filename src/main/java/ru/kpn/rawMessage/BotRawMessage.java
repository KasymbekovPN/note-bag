package ru.kpn.rawMessage;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// TODO: 27.11.2021 refactoring
@ToString
@EqualsAndHashCode
public class BotRawMessage implements RawMessage<String> {
    private final List<Object> args = new ArrayList<>();

    private String code;

    public BotRawMessage() {
        this.code = "";
    }

    public BotRawMessage(String code) {
        this.code = code;
    }

    // TODO: 20.11.2021 test
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public RawMessage<String> setCode(String code) {
        this.code = code;
        return null;
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
