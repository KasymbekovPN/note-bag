// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.creator.strategyInit;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import ru.kpn.objectExtraction.creator.Creator;
//import ru.kpn.objectExtraction.datum.StrategyInitDatum;
//import ru.kpn.objectExtraction.result.Result;
//import ru.kpn.objectExtraction.result.ResultImpl;
//
//@Component
//public class StrategyInitCreator implements Creator<StrategyInitDatum, Integer> {
//
//    private static final String NAME = "StrategyInitCreator";
//
//    @Override
//    public Result<Integer> create(StrategyInitDatum datum) {
//        return new Creator(new ResultImpl<>(), datum)
//                .checkDatumOnNull()
//                .checkPriorityOnNull()
//                .create();
//    }
//
//    @AllArgsConstructor
//    private static class Creator{
//        private final Result<Integer> result;
//        private final StrategyInitDatum datum;
//
//        public Creator checkDatumOnNull() {
//            if (result.getSuccess() && datum == null){
//                result.takeMessage("datum.isNull").add(NAME);
//            }
//            return this;
//        }
//
//        public Creator checkPriorityOnNull() {
//            if (result.getSuccess() && datum.getPriority() == null){
//                result.takeMessage("datum.priority.isNull").add(NAME);
//            }
//            return this;
//        }
//
//        public Result<Integer> create(){
//            if (result.getSuccess()){
//                result.setValue(datum.getPriority());
//            }
//            return result;
//        }
//    }
//}
