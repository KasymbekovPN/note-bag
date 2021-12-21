package ru.kpn.objectFactory.creator.strategyInit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpn.objectFactory.creator.AbstractTypedCreator;
import ru.kpn.objectFactory.datum.StrategyInitDatum;
import ru.kpn.objectFactory.result.ValuedResult;
import ru.kpn.objectFactory.results.builder.AbstractResultBuilder;
import ru.kpn.objectFactory.results.builder.ResultBuilder;
import ru.kpn.objectFactory.results.result.Result;
import ru.kpn.objectFactory.type.StrategyInitDatumType;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

@Component
public class StrategyInitCreator extends AbstractTypedCreator<StrategyInitDatumType, StrategyInitDatum, Integer, RawMessage<String>> {

    private static final String NAME = "StrategyInitCreator";
    private static final StrategyInitDatumType TYPE = new StrategyInitDatumType(StrategyInitDatumType.ALLOWED_TYPE.COMMON.name());

    @Override
    public StrategyInitDatumType getType() {
        return TYPE;
    }

    @Override
    protected AbstractResultBuilder<Integer, RawMessage<String>> createBuilder(StrategyInitDatum datum) {
        return new Builder(datum);
    }

    @AllArgsConstructor
    private static class Builder extends AbstractResultBuilder<Integer, RawMessage<String>>{
        private final StrategyInitDatum datum;

        @Override
        public ResultBuilder<Integer, RawMessage<String>> check() {
            checkDatumOnNull();
            checkPriorityOnNull();
            return this;
        }

        @Override
        public ResultBuilder<Integer, RawMessage<String>> calculateValue() {
            if (success){
                value = datum.getPriority();
            }
            return this;
        }

        @Override
        protected Result<Integer, RawMessage<String>> buildOnSuccess() {
            return new ValuedResult<>(value);
        }

        @Override
        protected Result<Integer, RawMessage<String>> buildOnFailure() {
            return new ValuedResult<>(success, status);
        }

        private void checkDatumOnNull() {
            if (success && datum == null){
                success = false;
                status = new BotRawMessage("datum.isNull").add(NAME);
            }
        }

        private void checkPriorityOnNull() {
            if (success && datum.getPriority() == null){
                success = false;
                status = new BotRawMessage("datum.priority.isNull").add(NAME);
            }
        }
    }
}
