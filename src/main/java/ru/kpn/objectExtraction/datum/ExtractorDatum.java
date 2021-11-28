package ru.kpn.objectExtraction.datum;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.objectExtraction.type.ExtractorDatumType;
import ru.kpn.objectFactory.datum.AbstractDatum;

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
