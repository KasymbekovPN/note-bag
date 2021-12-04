package ru.kpn.objectFactory.datum;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.objectFactory.type.StrategyInitDatumType;

@Getter
@Setter
public class StrategyInitDatum extends AbstractDatum<StrategyInitDatumType> {
    private Integer priority;

    @Override
    public void setType(String type) {
        this.type = new StrategyInitDatumType(type);
    }
}
