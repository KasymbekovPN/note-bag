package ru.kpn.statusSeed.seed;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StatusSeedImpl implements StatusSeed<String> {
    private final String code;
    private final Object[] args;

    public StatusSeedImpl(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public StatusSeedImpl(String code) {
        this.code = code;
        this.args = new Object[0];
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}
