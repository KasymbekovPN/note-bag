package ru.kpn.statusSeed;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// TODO: 23.12.2021 del
@ToString
@EqualsAndHashCode
public class BotRawMessageOld implements RawMessageOld<String> {
    private final List<Object> args = new ArrayList<>();

    private String code;

    public BotRawMessageOld() {
        this.code = "";
    }

    public BotRawMessageOld(String code) {
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
