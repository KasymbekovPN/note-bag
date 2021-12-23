package ru.kpn.exception;

import ru.kpn.statusSeed.RawMessageOld;

import java.util.ArrayList;
import java.util.List;

public class RawMessageOldException extends Exception implements RawMessageOld<String> {
    private final List<Object> args = new ArrayList<>();

    private String code;

    public RawMessageOldException(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public RawMessageOld<String> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public RawMessageOld<String> add(Object o) {
        args.add(o);
        return this;
    }

    @Override
    public Object[] getArgs() {
        return args.toArray();
    }
}
