package ru.kpn.logging;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.kpn.exception.CustomizableLoggerBuildException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomizableLoggerTest {

    private static Object[][] getLogLevelTestData(){
        return new Object[][]{
                {LogLevel.TRACE},
                {LogLevel.DEBUG},
                {LogLevel.INFO},
                {LogLevel.WARN},
                {LogLevel.ERROR},
        };
    }

    @Test
    @SneakyThrows
    void shouldBuildWithType() {
        CustomizableLogger logger = createMinBuilder().build();
        assertThat(logger.getType()).isEqualTo(this.getClass());
    }

    @Test
    void shouldThrowException() {
        assertThatThrownBy(() -> {
            CustomizableLogger.builder().build();
        })
        .isInstanceOf(CustomizableLoggerBuildException.class);
    }

    @ParameterizedTest
    @MethodSource("getLogLevelTestData")
    @SneakyThrows
    void shouldBuildWithDefaultLevel(LogLevel logLevel) {
        Logger logger = createMinBuilder().build();
        assertThat(logger.isEnabledLevel(logLevel)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("getLogLevelTestData")
    @SneakyThrows
    void shouldBuildWithEnabledLevel(LogLevel logLevel) {
        Logger logger = createMinBuilder().enableLevel(logLevel).build();
        assertThat(logger.isEnabledLevel(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getLogLevelTestData")
    @SneakyThrows
    void shouldSetEnabledLevel(LogLevel logLevel) {
        Logger logger = createMinBuilder().build();
        logger.enableLevel(logLevel);
        assertThat(logger.isEnabledLevel(logLevel)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("getLogLevelTestData")
    @SneakyThrows
    void shouldSetDisabledLevel(LogLevel logLevel) {
        Logger logger = createMinBuilder().enableLevel(logLevel).build();
        logger.disableLevel(logLevel);
        assertThat(logger.isEnabledLevel(logLevel)).isFalse();
    }

    private CustomizableLogger.CustomizableLoggerBuilder createMinBuilder(){
        return CustomizableLogger.builder().type(this.getClass());
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
