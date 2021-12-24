package ru.kpn.seed;

import java.util.ArrayList;
import java.util.List;

class StringSeedBuilder implements SeedBuilder<String> {
    private final List<Object> args = new ArrayList<>();
    private String code;

    @Override
    public SeedBuilder<String> code(String code) {
        this.code = code;
        return this;
    }

    @Override
    public SeedBuilder<String> arg(Object arg) {
        this.args.add(arg);
        return this;
    }

    @Override
    public Seed<String> build() {
        return new StringSeed(code, args.toArray());
    }
}
