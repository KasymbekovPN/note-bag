package ru.kpn.objectFactory.result;

import ru.kpn.rawMessage.RawMessage;

// TODO: 11.12.2021 manage status field through ValuedResult methods 
// TODO: 06.12.2021 del setter out of Result interface ???
public class ValuedResult<V> extends AbstractResult<V, RawMessage<String>> {

    public ValuedResult(V value) {
        this.success = true;
        this.value = value;
    }

    public ValuedResult(RawMessage<String> status) {
        this.success = false;
        this.status = status;
    }

    @Override
    public void setSuccess(Boolean success) {}

    @Override
    public void setValue(V value) {}

    @Override
    public void setStatus(RawMessage<String> status) {}
}
