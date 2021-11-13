package ru.kpn.rawMessage;

import org.springframework.stereotype.Service;

@Service
public class BotRawMessageFactory implements RawMessageFactory<String> {
    @Override
    public RawMessage<String> create(String code) {
        return new BotRawMessage(code);
    }
}
