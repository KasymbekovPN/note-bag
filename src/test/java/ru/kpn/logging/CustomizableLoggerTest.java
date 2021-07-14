package ru.kpn.logging;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizableLoggerTest {

    private static Object[][] getEnum(){
        return new Object[][]{
                {CustomizableLogger.LogLevel.TRACE},
                {CustomizableLogger.LogLevel.DEBUG},
                {CustomizableLogger.LogLevel.INFO},
                {CustomizableLogger.LogLevel.WARN},
                {CustomizableLogger.LogLevel.ERROR},
        };
    }

    @Test
    @SneakyThrows
    void shouldBuildWithType() {
        CustomizableLogger logger = createMinBuilder().build();
        assertThat(logger.getType()).isEqualTo(this.getClass());
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldBuildWithDefaultLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().build();
        assertThat(logger.isEnabled(logLevel)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldBuildWithEnableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().enable(logLevel).build();
        assertThat(logger.isEnabled(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldEnableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().build();
        logger.enable(logLevel);
        assertThat(logger.isEnabled(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getEnum")
    void shouldDisableLogLevel(CustomizableLogger.LogLevel logLevel) {
        Logger<CustomizableLogger.LogLevel> logger = createMinBuilder().enable(logLevel).build();
        logger.disable(logLevel);
        assertThat(logger.isEnabled(logLevel)).isFalse();
    }

    private CustomizableLogger.CustomizableLoggerBuilder createMinBuilder(){
        return CustomizableLogger.builder(this.getClass());
    }


    //<
    //    @Test
//    void shouldBuildWithClass() throws IOException {
////        CustomizableLogger.builder()
////                .classType(this.getClass())
////                .build();
//
//
////        FileOutputStream fileOutputStream = new FileOutputStream("text.txt");
////        FileChannel channel = fileOutputStream.getChannel();
//
//        FileOutputStream stdout = new FileOutputStream(FileDescriptor.out);
//        FileChannel stdoutChannel = stdout.getChannel();
//
//        FileOutputStream stdout1 = new FileOutputStream(FileDescriptor.out);
//        FileChannel stdoutChannel1 = stdout1.getChannel();
//
////        String s = "Hello Привет";
////        ByteBuffer byteBuffer = ByteBuffer.wrap(s.getBytes(StandardCharsets.UTF_8));
//
////        String s1 = StandardCharsets.UTF_8.decode(byteBuffer).toString();
////        System.out.println(s1);
//
//        List<String> strings = List.of("1", "2", "3");
//
//        for (String string : strings) {
//            ByteBuffer byteBuffer = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
////            byteBuffer.flip();
//            stdoutChannel.write(byteBuffer);
//            ByteBuffer byteBuffer1 = ByteBuffer.wrap(string.getBytes(StandardCharsets.UTF_8));
//            stdoutChannel1.write(byteBuffer1);
//        }
//
////        byteBuffer.flip();
////        stdoutChannel.write(byteBuffer);
////
////        byteBuffer.flip();
////        channel.write(byteBuffer);
////
////        byteBuffer.flip();
////        stdoutChannel1.write(byteBuffer);
//
////        System.out.println(s1);
//
//
//        //        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//
//
////        String s = StandardCharsets.UTF_8.decode(byteBuffer).toString();
//
////        CharBuffer cb = CharBuffer.allocate(1024);
////        cb.append("Hello");
//
////        stdoutChannel.write()
//
////        FileInputStream stdin = new FileInputStream(FileDescriptor.in);
////        FileChannel stdinChannel = stdin.getChannel();
//
//    }
}
