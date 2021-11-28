package ru.kpn.objectExtraction.creator.strategyInit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpn.objectExtraction.datum.StrategyInitDatum;
import ru.kpn.objectExtraction.result.OptimisticResult;
import ru.kpn.objectFactory.creator.Creator;
import ru.kpn.objectFactory.result.Result;
import ru.kpn.rawMessage.RawMessage;

@Component
public class StrategyInitCreator implements Creator<StrategyInitDatum, Integer, RawMessage<String>> {
    private static final String NAME = "StrategyInitCreator";

    @Override
    public Result<Integer, RawMessage<String>> create(StrategyInitDatum datum) {
        return new InnerCreator(new OptimisticResult<>(), datum)
                .checkDatumOnNull()
                .checkPriorityOnNull()
                .create();
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
