package ru.kpn.objectFactory.datum;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.objectFactory.type.ExtractorDatumType;

import java.util.Set;

@Getter
@Setter
public class ExtractorDatum extends AbstractDatum<ExtractorDatumType> {
    private Set<String> prefixes;

    @Override
    public void setType(String type) {
        this.type = new ExtractorDatumType(type);
    }
}
