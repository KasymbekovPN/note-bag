// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.builder;
//
//import ru.kpn.objectExtraction.datum.StrategyInitDatum;
//import ru.kpn.objectExtraction.factory.ObjectFactory;
//import ru.kpn.objectExtraction.factory.StrategyInitFactory;
//import ru.kpn.rawMessage.RawMessageFactory;
//
//public class StrategyInitBuilder extends BaseBuilder<StrategyInitDatum, Integer, StrategyInitFactory.Type> {
//
//    public StrategyInitBuilder(ObjectFactory<StrategyInitFactory.Type, Integer> objectFactory, RawMessageFactory<String> messageFactory) {
//        super(objectFactory, messageFactory);
//    }
//
//    @Override
//    public Builder<StrategyInitDatum, Integer> doScenario() {
//        checkDatumExistence();
//        checkAndPrepareArgs();
//        return this;
//    }
//
//    private void checkDatumExistence() {
//        if (result.getSuccess() && datum == null){
//            result.makeFailure(messageFactory.create("data.notExist.forSth").add(key));
//        }
//    }
//
//    private void checkAndPrepareArgs() {
//        if (result.getSuccess()){
//            if (datum.getPriority() != null){
//                args = new Object[]{datum.getPriority()};
//            } else {
//                result.makeFailure(messageFactory.create("arguments.invalid.forSth").add(key));
//            }
//        }
//    }
//}
