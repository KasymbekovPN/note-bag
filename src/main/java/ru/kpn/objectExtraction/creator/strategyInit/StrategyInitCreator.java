package ru.kpn.objectExtraction.creator.strategyInit;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpn.objectExtraction.creator.Creator;
import ru.kpn.objectExtraction.result.Result;
import ru.kpn.objectExtraction.result.ResultImpl;

@Component
public class StrategyInitCreator implements Creator<Integer> {

    private static final String NAME = "StrategyInitCreator";
    private static final int ARGS_SIZE = 1;

    @Override
    public Result<Integer> create(Object... args) {
        return new Checker(new ResultImpl<>(), args)
                .checkOnNull()
                .checkArgsSize()
                .checkFirstArg()
                .getResult();
    }

    @AllArgsConstructor
    private static class Checker{
        private final Result<Integer> result;
        private final Object[] args;

        private Checker checkOnNull(){
            if (result.getSuccess() && args == null){
                result.takeMessage("arguments.isNull").add(NAME);
            }
            return this;
        }

        private Checker checkArgsSize(){
            if (result.getSuccess() && ARGS_SIZE != args.length){
                result.takeMessage("arguments.invalid.size").add(NAME).add(args.length).add(ARGS_SIZE);
            }
            return this;
        }
        
        private Checker checkFirstArg(){
            if (result.getSuccess()){
                try{
                    result.setValue((Integer) args[0]);
                } catch (Exception ex){
                    result.takeMessage("argument.wrongType").add(NAME);
                }
            }
            return this;
        }

        private Result<Integer> getResult(){
            return result;
        }
    }
}
