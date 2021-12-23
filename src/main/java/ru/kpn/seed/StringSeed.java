package ru.kpn.seed;

import lombok.EqualsAndHashCode;

// TODO: 23.12.2021 make it package-private 
@EqualsAndHashCode
class StringSeed implements Seed<String> {
    private final String code;
    private final Object[] args;

    public StringSeed(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public StringSeed(String code) {
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
