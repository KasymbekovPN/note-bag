package ru.kpn.objectFactory.creator.strategyInit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpn.objectFactory.creator.CreatorWithType;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.result.builder.ResultBuilder;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

@Component
public class StrategyInitCreator implements CreatorWithType<StrategyInitDatum, StrategyInitDatumType, Integer, RawMessage<String>> {

    private static final String NAME = "StrategyInitCreator";
    private static final StrategyInitDatumType TYPE = new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());

    @Override
    public Result<Integer, RawMessage<String>> create(StrategyInitDatum datum) {
        return new ResultBuilderImpl(datum)
                .checkDatumOnNull()
                .checkPriorityOnNull()
                .calculateValue()
                .build();
    }

    @Override
    public StrategyInitDatumType getType() {
        return TYPE;
    }

    @AllArgsConstructor
    private static class ResultBuilderImpl extends AbstractResultBuilder<Integer> {
        private final StrategyInitDatum datum;

        public ResultBuilderImpl checkDatumOnNull(){
            if (success && datum == null){
                success = false;
                status.setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public ResultBuilderImpl checkPriorityOnNull() {
            if (success && datum.getPriority() == null){
                success = false;
                status.setCode("datum.priority.isNull").add(NAME);
            }
            return this;
        }

        @Override
        public ResultBuilder<Integer> calculateValue() {
            if (success){
                value = datum.getPriority();
            }
            return this;
        }
    }
}
