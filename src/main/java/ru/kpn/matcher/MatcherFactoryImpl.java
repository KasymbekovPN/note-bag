package ru.kpn.matcher;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MatcherFactoryImpl implements MatcherFactory<String, Boolean> {

    private static final EnumMap<MatcherType, Function<Object[], Function<String, Boolean>>> creators = new EnumMap<>(MatcherType.class){{
        put(MatcherType.DEFAULT, MatcherFactoryImpl::createDefaultMatcher);
        put(MatcherType.CONSTANT, MatcherFactoryImpl::createPersistentMatcher);
        put(MatcherType.REGEX, MatcherFactoryImpl::createRegexMatcher);
        put(MatcherType.MULTI_REGEX, MatcherFactoryImpl::createMultiRegexMatcher);
    }};

    @Override
    public Function<String, Boolean> create(MatcherType matcherType, Object... args) {
        return creators.get(matcherType).apply(args);
    }

    private static Function<String, Boolean> createDefaultMatcher(Object... args){
        return new ConstantMatcher(false);
    }

    private static Function<String, Boolean> createPersistentMatcher(Object... args){
        boolean result =  args.length > 0 && args[0].getClass().equals(Boolean.class)
                ? (Boolean) args[0]
                : false;
        return new ConstantMatcher(result);
    }

    private static Function<String, Boolean> createRegexMatcher(Object... args){
        if (args.length > 0 && args[0].getClass().equals(String.class)){
            return new RegexMatcher(String.valueOf(args[0]));
        }
        return createDefaultMatcher(args);
    }

    private static Function<String, Boolean> createMultiRegexMatcher(Object... args){
        Set<String> templates = Arrays.stream(args).map(String::valueOf).collect(Collectors.toSet());
        return new MultiRegexMatcher(templates);
    }
}
