package ru.kpn.objectExtraction.result;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.rawMessage.RawMessage;

@Getter
@Setter
public class ResultImpl<T> implements Result<T> {
    private Boolean success;
    private RawMessage<String> rawMessage;
    private T value;
}
