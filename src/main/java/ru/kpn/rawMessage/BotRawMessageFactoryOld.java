package ru.kpn.rawMessage;

import org.springframework.stereotype.Service;

// TODO: 23.12.2021 del
@Service
public class BotRawMessageFactoryOld implements RawMessageFactoryOld<String> {
    @Override
    public RawMessageOld<String> create(String code) {
        return new BotRawMessageOld(code);
    }
}
