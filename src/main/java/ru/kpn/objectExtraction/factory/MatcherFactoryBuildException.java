package ru.kpn.objectExtraction.factory;

import ru.kpn.rawMessage.RawMessage;

import java.util.ArrayList;
import java.util.List;

// TODO: 16.11.2021 need base class
public class MatcherFactoryBuildException extends Exception implements RawMessage<String> {
    private final String code;
    private final List<Object> args = new ArrayList<>();

    public MatcherFactoryBuildException(String code) {
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
