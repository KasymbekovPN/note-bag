package ru.kpn.i18n.adapter.resizer;

import java.util.function.Function;

public class Resizer implements Function<Object[], Object[]> {

    private final Integer size;

    public Resizer(Integer size) {
        this.size = size;
    }

    @Override
    public Object[] apply(Object[] objects) {
        Object[] resultArray = new Object[size];
        int end = size <= objects.length ? size : objects.length;
        System.arraycopy(objects, 0, resultArray, 0, end);
        return resultArray;
    }
}
