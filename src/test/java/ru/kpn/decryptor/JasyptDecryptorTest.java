package ru.kpn.decryptor;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JasyptDecryptorTest {

    private static final String PASSWORD = "some-secret-string";

    private static Object[][] getTestData(){
        Set<String> raw = Set.of(
                "hello",
                "world",
                "!!!",
                "hello, world",
                "hello, world !!!"
        );

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(PASSWORD);

        Object[][] objects = new Object[raw.size()][2];
        int count = 0;
        for (String s : raw) {
            objects[count][0] = encryptor.encrypt(s);
            objects[count++][1] = s;
        }

        return objects;
    }

    private Decryptor decryptor;

    @BeforeEach
    void setUp() {
        decryptor = new JasyptDecryptor(PASSWORD);
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void shouldCheckDecryption(String encoded, String raw) {
        String line = decryptor.decrypt(encoded);
        assertThat(line).isEqualTo(raw);
    }
}
