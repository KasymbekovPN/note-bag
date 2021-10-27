package ru.kpn.matcher;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@AllArgsConstructor
public class ConstantMatcher implements Function<Update, Boolean> {
    private final boolean matchingResult;

    @Override
    public Boolean apply(Update update) {
        return matchingResult;
    }
}
