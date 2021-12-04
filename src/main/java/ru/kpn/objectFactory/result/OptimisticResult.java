package ru.kpn.objectFactory.result;

import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

// TODO: 28.11.2021 refactoring + test it
public class OptimisticResult<V> extends AbstractResult<V, RawMessage<String>> {

    public OptimisticResult() {
        this.success = true;
        this.status = new BotRawMessage();
    }

    public OptimisticResult(Boolean success, RawMessage<String> status, V value) {
        this.success = success;
        this.status = status;
        this.value = value;
    }

    public RawMessage<String> toFailAndGetStatus(){
        this.success = false;
        return status;
    }

    // TODO: 28.11.2021 del 
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    // TODO: 28.11.2021 del
    public static class Builder<V>{
        private Boolean success = true;
        private RawMessage<String> status = new BotRawMessage();
        private V value;

        // TODO: 28.11.2021 del
        public Builder<V> success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder<V> status(RawMessage<String> status){
            // TODO: 28.11.2021 ???
            this.success = false;

            this.status = status;
            return this;
        }

        public Builder<V> value(V value){
            // TODO: 28.11.2021 ???
            this.success = true;

            this.value = value;
            return this;
        }

        public OptimisticResult<V> build(){
            // TODO: 28.11.2021 ???
//            check();
            return new OptimisticResult<>(success, status, value);
        }


//        private void check() {
//            success = success && value != null;
//            if (!success && status.getCode().isEmpty()){
//                status.setCode("success==true.value==null.onResultCreation");
//            }
//        }
    }
}
