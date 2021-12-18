package ru.kpn.rawMessage;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public RawMessage<String> setCode(String code) {
        this.code = code;
        return this;
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
