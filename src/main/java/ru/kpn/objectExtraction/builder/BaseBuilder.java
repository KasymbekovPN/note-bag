// TODO: 27.11.2021 del
//package ru.kpn.objectExtraction.builder;
//
//import lombok.RequiredArgsConstructor;
//import ru.kpn.objectExtraction.factory.ObjectFactory;
//import ru.kpn.objectExtraction.result.Result;
//import ru.kpn.objectExtraction.result.ResultImpl;
//import ru.kpn.rawMessage.RawMessageFactory;
//
//@RequiredArgsConstructor
//abstract public class BaseBuilder<D, RT, T> implements Builder<D, RT>{
//
//    protected final ObjectFactory<T, RT> objectFactory;
//    protected final RawMessageFactory<String> messageFactory;
//
//    protected Result<RT> result;
//    protected String key;
//    protected D datum;
//    protected T type;
//    protected Object[] args;
//
//    @Override
//    public Builder<D, RT> start(String key) {
//        reset();
//        this.result = new ResultImpl<>();
//        this.key = key;
//        return this;
//    }
//
//    @Override
//    public Builder<D, RT> datum(D datum) {
//        this.datum = datum;
//        return this;
//    }
//
//    @Override
//    public Result<RT> build() {
//        if (result.getSuccess()){
//            result.setValue(objectFactory.create(type, args));
//        }
//        return result;
//    }
//
//    protected void reset() {
//        datum = null;
//        type = null;
//        args = null;
//    }
//}
