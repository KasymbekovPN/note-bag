package ru.kpn.objectExtraction.datum;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MatcherDatum {
    private String type;
    private Boolean constant;
    private String template;
    private Set<String> templates;
}
