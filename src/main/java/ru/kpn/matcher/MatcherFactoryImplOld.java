package ru.kpn.matcher;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: 18.10.2021 del
@Service
public class MatcherFactoryImplOld implements MatcherFactory<String, Boolean> {

    private static final EnumMap<MatcherType, Function<Object[], Function<String, Boolean>>> creators = new EnumMap<>(MatcherType.class){{
//        put(MatcherType.DEFAULT, MatcherFactoryImplOld::createDefaultMatcher);
        put(MatcherType.CONSTANT, MatcherFactoryImplOld::createPersistentMatcher);
        put(MatcherType.REGEX, MatcherFactoryImplOld::createRegexMatcher);
        put(MatcherType.MULTI_REGEX, MatcherFactoryImplOld::createMultiRegexMatcher);
    }};

    @Override
    public Function<String, Boolean> create(MatcherType matcherType, Object... args) {
        return creators.get(matcherType).apply(args);
    }

    private static Function<String, Boolean> createDefaultMatcher(Object... args){
        return new ConstantMatcherOld(false);
    }

    private static Function<String, Boolean> createPersistentMatcher(Object... args){
        boolean result =  args.length > 0 && args[0].getClass().equals(Boolean.class)
                ? (Boolean) args[0]
                : false;
        return new ConstantMatcherOld(result);
    }

    private static Function<String, Boolean> createRegexMatcher(Object... args){
        if (args.length > 0 && args[0].getClass().equals(String.class)){
            return new RegexMatcherOld(String.valueOf(args[0]));
        }
        return createDefaultMatcher(args);
    }

    private static Function<String, Boolean> createMultiRegexMatcher(Object... args){
        Set<String> templates = Arrays.stream(args).map(String::valueOf).collect(Collectors.toSet());
        return new MultiRegexMatcherOLd(templates);
    }
}
