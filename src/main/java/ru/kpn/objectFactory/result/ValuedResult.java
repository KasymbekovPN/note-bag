package ru.kpn.objectFactory.result;

import ru.kpn.rawMessage.RawMessage;

public class ValuedResult<V> extends AbstractResult<V, RawMessage<String>> {

    public ValuedResult(V value) {
        this.success = true;
        this.value = value;
    }

    public ValuedResult(RawMessage<String> status) {
        this.success = false;
        this.status = status;
    }

    public ValuedResult(Boolean success, RawMessage<String> status){
        this.success = success;
        this.status = status;
    }

    public ValuedResult(Boolean success, V value, RawMessage<String> status) {
        this.success = success;
        this.value = value;
        this.status = status;
    }

    @Override
    public void setSuccess(Boolean success) {}

    @Override
    public void setValue(V value) {}

    @Override
    public void setStatus(RawMessage<String> status) {}
}
