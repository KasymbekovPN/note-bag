package ru.kpn.objectFactory.datum;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.objectFactory.type.MatcherDatumType;

import java.util.Set;

@Getter
@Setter
public class MatcherDatum extends AbstractDatum<MatcherDatumType> {
    private Boolean constant;
    private String template;
    private Set<String> templates;

    @Override
    public void setType(String type) {
        this.type = new MatcherDatumType(type);
    }
}
