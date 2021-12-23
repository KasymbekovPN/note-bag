package ru.kpn.rawMessage;

public class SimpleRawSource implements RawSource<String> {
    private final String code;
    private final Object[] args;

    public SimpleRawSource(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public SimpleRawSource(String code) {
        this.code = code;
        this.args = new Object[0];
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}
