package ru.kpn.objectExtraction.result;

import ru.kpn.objectFactory.result.AbstractResult;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

public class OptimisticResult<V> extends AbstractResult<V, RawMessage<String>> {

    private OptimisticResult(Boolean success, RawMessage<String> status, V value) {
        this.success = success;
        this.status = status;
        this.value = value;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<V>{
        private Boolean success = true;
        private RawMessage<String> status = new BotRawMessage();
        private V value;

        public Builder<V> success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder<V> status(RawMessage<String> status){
            this.status = status;
            return this;
        }

        public Builder<V> value(V value){
            this.value = value;
            return this;
        }

        public OptimisticResult<V> build(){
            check();
            return new OptimisticResult<>(success, status, value);
        }

        private void check() {
            success = success && value != null;
            if (!success && status.getCode().isEmpty()){
                status.setCode("success==true.value==null.onResultCreation");
            }
        }
    }
}
