package ru.kpn.statusSeed.builder;

import ru.kpn.statusSeed.seed.StatusSeed;
import ru.kpn.statusSeed.seed.StringStatusSeed;

import java.util.ArrayList;
import java.util.List;

// TODO: 23.12.2021 make it package-private
public class StringStatusSeedBuilder implements StatusSeedBuilder<String> {
    private final String code;
    private final List<Object> args = new ArrayList<>();

    public StringStatusSeedBuilder(String code) {
        this.code = code;
    }

    @Override
    public StatusSeedBuilder<String> arg(Object arg) {
        this.args.add(arg);
        return this;
    }

    @Override
    public StatusSeed<String> build() {
        return new StringStatusSeed(code, args.toArray());
    }
}
