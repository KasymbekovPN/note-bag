package ru.kpn.objectFactory.type;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MatcherDatumType extends AbstractDatumType {

    public MatcherDatumType(String strType) {
        super(strType);
    }

    @Override
    protected Boolean checkValidity() {
        Set<String> allowedTypeNames = Arrays.stream(ALLOWED_TYPE.values()).map(Enum::name).collect(Collectors.toSet());
        return allowedTypeNames.contains(strType);
    }

    public String asStr() {
        return strType;
    }

    public enum ALLOWED_TYPE {
        CONSTANT,
        REGEX,
        MULTI_REGEX
    }
}
