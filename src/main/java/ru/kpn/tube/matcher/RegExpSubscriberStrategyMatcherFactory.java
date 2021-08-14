package ru.kpn.tube.matcher;

import org.springframework.stereotype.Service;
import ru.kpn.tube.strategy.Matcher;

@Service
public class RegExpSubscriberStrategyMatcherFactory {
    public Matcher create(String template){
        return new RegExpSubscriberStrategyMatcher(template);
    }
}
