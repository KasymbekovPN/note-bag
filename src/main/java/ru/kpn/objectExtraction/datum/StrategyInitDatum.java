package ru.kpn.objectExtraction.datum;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.objectExtraction.type.StrategyInitDatumType;
import ru.kpn.objectFactory.datum.AbstractDatum;

@Getter
@Setter
public class StrategyInitDatum extends AbstractDatum<StrategyInitDatumType> {
    private Integer priority;

    @Override
    public void setType(String type) {
        this.type = new StrategyInitDatumType(type);
    }
}
