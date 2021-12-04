package ru.kpn.objectExtraction.datum;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.objectExtraction.type.MatcherDatumType;
import ru.kpn.objectFactory.datum.AbstractDatum;

import java.util.Set;

@Getter
@Setter
public class MatcherDatum extends AbstractDatum<MatcherDatumType> {
    private Boolean constant;
    private String template;
    private Set<String> templates;

    @Override
    public void setType(String type) {
        //<
        System.out.println("matcher type: " + type);
        //<
        this.type = new MatcherDatumType(type);
    }
}
