package ru.kpn.objectExtraction.datum;

import ru.kpn.objectExtraction.type.ExtractorDatumType;
import ru.kpn.objectFactory.datum.AbstractDatum;

import java.util.Set;

public class ExtractorDatum extends AbstractDatum<ExtractorDatumType> {
    private Set<String> prefixes;

    @Override
    public void setType(String type) {
        this.type = new ExtractorDatumType(type);
    }

    public Set<String> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(Set<String> prefixes) {
        this.prefixes = prefixes;
    }
}
