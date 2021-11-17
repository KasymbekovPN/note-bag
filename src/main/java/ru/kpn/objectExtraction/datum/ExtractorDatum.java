package ru.kpn.objectExtraction.datum;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

// TODO: 17.11.2021 del
@Getter
@Setter
public class ExtractorDatum {
    private String type;
    private Set<String> prefixes;
}
