package ru.kpn.decryptor;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptDecryptor implements Decryptor {

    private final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public JasyptDecryptor(String password) {
        this.encryptor.setPassword(password);
    }

    @Override
    public String decrypt(String encoded) {
        return encryptor.decrypt(encoded);
    }
}
