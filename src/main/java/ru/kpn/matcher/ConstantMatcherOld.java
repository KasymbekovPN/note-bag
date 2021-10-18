package ru.kpn.matcher;

import java.util.function.Function;

// TODO: 18.10.2021 del 
class ConstantMatcherOld implements Function<String, Boolean> {

    private final boolean result;

    public ConstantMatcherOld(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean apply(String value) {
        return result;
    }
}
