package ru.kpn.objectExtraction.result;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.rawMessage.RawMessage;

// TODO: 13.11.2021 test 
@Getter
@Setter
public class ResultImpl<T> implements Result<T> {
    private Boolean success;
    private RawMessage<String> rawMessage;
    private T value;
}
