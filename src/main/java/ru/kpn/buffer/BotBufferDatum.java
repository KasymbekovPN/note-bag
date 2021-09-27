package ru.kpn.buffer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BotBufferDatum implements BufferDatum<BufferDatumType, String> {
    private final BufferDatumType type;
    private final String content;

    @Override
    public BufferDatumType getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }
}
