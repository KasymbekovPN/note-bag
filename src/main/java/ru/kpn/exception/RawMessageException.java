package ru.kpn.exception;

import ru.kpn.rawMessage.RawMessage;

import java.util.ArrayList;
import java.util.List;

public class RawMessageException extends Exception implements RawMessage<String> {
    private final String code;
    private final List<Object> args = new ArrayList<>();

    public RawMessageException(String code) {
        this.code = code;
    }

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
