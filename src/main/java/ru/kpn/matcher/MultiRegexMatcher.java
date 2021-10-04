package ru.kpn.matcher;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

class MultiRegexMatcher implements Function<String, Boolean> {

    private final Set<Pattern> patterns = new HashSet<>();

    public MultiRegexMatcher(Set<String> templates) {
        for (String template : templates) {
            patterns.add(Pattern.compile(template));
        }
    }

    @Override
    public Boolean apply(String value) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(value).matches()){
                return true;
            }
        }
        return false;
    }
}
