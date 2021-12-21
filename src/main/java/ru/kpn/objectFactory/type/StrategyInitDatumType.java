package ru.kpn.objectFactory.type;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StrategyInitDatumType extends AbstractDatumType {

    public StrategyInitDatumType(String strType) {
        super(strType);
    }

    @Override
    protected Boolean checkValidity() {
        Set<String> allowedTypeNames = Arrays.stream(ALLOWED_TYPE.values()).map(Enum::name).collect(Collectors.toSet());
        return allowedTypeNames.contains(strType);
    }

    public enum ALLOWED_TYPE {
        COMMON
    }
}
