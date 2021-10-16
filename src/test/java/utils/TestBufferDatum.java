package utils;

import ru.kpn.buffer.BufferDatum;
import ru.kpn.buffer.BufferDatumType;

public class TestBufferDatum implements BufferDatum<BufferDatumType, String>{

    private BufferDatumType type;
    private String content;

    public TestBufferDatum() {
    }

    public TestBufferDatum(BufferDatumType type) {
        this.type = type;
    }

    public TestBufferDatum(String content) {
        this.content = content;
    }

    public TestBufferDatum(BufferDatumType type, String content) {
        this.type = type;
        this.content = content;
    }

    @Override
    public BufferDatumType getType() {
        return type;
    }

    @Override
    public String getContent() {
        return content;
    }
}
