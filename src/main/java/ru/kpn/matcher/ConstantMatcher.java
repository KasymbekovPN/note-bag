package ru.kpn.matcher;

import java.util.function.Function;

class ConstantMatcher implements Function<String, Boolean> {

    private final boolean result;

    public ConstantMatcher(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean apply(String value) {
        return result;
    }
}
