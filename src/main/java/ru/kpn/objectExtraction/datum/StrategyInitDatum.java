package ru.kpn.objectExtraction.datum;

import ru.kpn.objectExtraction.type.StrategyInitDatumType;
import ru.kpn.objectFactory.datum.AbstractDatum;

public class StrategyInitDatum extends AbstractDatum<StrategyInitDatumType> {
    private Integer priority;

    @Override
    public void setType(String type) {
        this.type = new StrategyInitDatumType(type);
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
