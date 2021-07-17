package ru.kpn.logging;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

class SoutWriter implements Writer {

    @Override
    public void write(String log) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(log.getBytes(StandardCharsets.UTF_8));
        FileChannel channel = new FileOutputStream(FileDescriptor.out).getChannel();
        channel.write(byteBuffer);
    }
}
