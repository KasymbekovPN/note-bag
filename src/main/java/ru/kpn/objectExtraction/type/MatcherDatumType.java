package ru.kpn.objectExtraction.type;

import ru.kpn.objectFactory.type.AbstractDatumType;

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

    // TODO: 04.12.2021 to base
    public String asStr() {
        return strType;
    }

    public enum ALLOWED_TYPE {
        CONSTANT,
        REGEX,
        MULTI_REGEX
    }
}
