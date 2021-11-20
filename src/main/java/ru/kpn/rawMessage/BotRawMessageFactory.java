package ru.kpn.rawMessage;

import org.springframework.stereotype.Service;

// TODO: 20.11.2021 del ??? 
@Service
public class BotRawMessageFactory implements RawMessageFactory<String> {
    @Override
    public RawMessage<String> create(String code) {
        return new BotRawMessage(code);
    }
}
