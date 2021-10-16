package ru.kpn.strategyCalculator;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class BotStrategyCalculatorSource implements StrategyCalculatorSource<String> {
    private final String code;
    private final List<Object> args = new ArrayList<>();

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void add(Object o) {
        args.add(o);
    }

    @Override
    public Object[] getArgs() {
        return args.toArray();
    }
}
