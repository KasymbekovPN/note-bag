package ru.kpn.extractor;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ByPrefixExtractor implements Function<Update, String> {

    private final List<String> prefixes;

    @Override
    public String apply(Update update) {
        String text = getText(update);
        return getExtraction(text);
    }

    private String getExtraction(String text) {
        for (String prefix : prefixes) {
            int prefixLen = prefix.length();
            if (prefixLen < text.length() && prefix.equals(text.substring(0, prefixLen))){
                return text.substring(prefixLen);
            }
        }
        return "";
    }

    private String getText(Update update) {
        return update.getMessage().getText();
    }
}
