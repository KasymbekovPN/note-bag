package ru.kpn.objectExtraction.result;

import lombok.Getter;
import lombok.Setter;
import ru.kpn.rawMessage.BotRawMessage;
import ru.kpn.rawMessage.RawMessage;

@Getter
@Setter
public class ResultImpl<T> implements Result<T> {
    private Boolean success = true;
    private RawMessage<String> rawMessage;
    private T value;

    // TODO: 20.11.2021 test it
    @Override
    public void makeFailure(RawMessage<String> rawMessage) {
        this.success = false;
        this.rawMessage = rawMessage;
    }

    // TODO: 20.11.2021 test it
    @Override
    public RawMessage<String> takeMessage(String code) {
        if (rawMessage == null){
            rawMessage = new BotRawMessage(code);
        }
        success = false;
        return rawMessage;
    }
}
