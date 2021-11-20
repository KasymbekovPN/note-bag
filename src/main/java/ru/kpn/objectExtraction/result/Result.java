package ru.kpn.objectExtraction.result;

import ru.kpn.rawMessage.RawMessage;

public interface Result<T> {
    Boolean getSuccess();
    RawMessage<String> getRawMessage();
    T getValue();
    void setSuccess(Boolean success);
    void setRawMessage(RawMessage<String> rawMessage);
    void setValue(T value);
    void makeFailure(RawMessage<String> rawMessage);
}
