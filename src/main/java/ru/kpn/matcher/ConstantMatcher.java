package ru.kpn.matcher;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

public class ConstantMatcher implements Function<Update, Boolean> {
    private final boolean result;

    public ConstantMatcher(boolean result) {
        this.result = result;
    }

    @Override
    public Boolean apply(Update update) {
        return result;
    }
}
