package ru.kpn.objectFactory.creator.strategyInit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpn.objectFactory.creator.CreatorWithType;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.OptimisticResult;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

@Component
public class StrategyInitCreator implements CreatorWithType<StrategyInitDatum, StrategyInitDatumType, Integer, RawMessage<String>> {

    private static final String NAME = "StrategyInitCreator";
    private static final StrategyInitDatumType TYPE = new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());

    @Override
    public Result<Integer, RawMessage<String>> create(StrategyInitDatum datum) {
        return new InnerCreator(new OptimisticResult<>(), datum)
                .checkDatumOnNull()
                .checkPriorityOnNull()
                .create();
    }

    @Override
    public StrategyInitDatumType getType() {
        return TYPE;
    }

    @AllArgsConstructor
    private static class InnerCreator{
        private OptimisticResult<Integer> result;
        private StrategyInitDatum datum;

        public Result<Integer, RawMessage<String>> create() {
            if (result.getSuccess()){
                result.setValue(datum.getPriority());
            }
            return result;
        }

        public InnerCreator checkDatumOnNull() {
            if (result.getSuccess() && datum == null){
                result.toFailAndGetStatus().setCode("datum.isNull").add(NAME);
            }
            return this;
        }

        public InnerCreator checkPriorityOnNull() {
            if (result.getSuccess() && datum.getPriority() == null){
                result.toFailAndGetStatus().setCode("datum.priority.isNull").add(NAME);
            }
            return this;
        }
    }
}
