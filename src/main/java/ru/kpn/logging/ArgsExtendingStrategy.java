package ru.kpn.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ArgsExtendingStrategy implements ExtendingStrategy<Object[]> {

    @Override
    public Object[] execute(Object[] value, Object... extension) {
        Thread thread = Thread.currentThread();
        int size = value.length + 5;
        Object[] extendedArgs = new Object[size];
        extendedArgs[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        extendedArgs[1] = getLogLevel(extension);
        extendedArgs[2] = thread.getId();
        extendedArgs[3] = thread.getName();
        extendedArgs[4] = getTypeName(extension);

        System.arraycopy(value, 0, extendedArgs, 5, value.length);

        return extendedArgs;
    }

    private CustomizableLogger.LogLevel getLogLevel(Object[] extensions) {
        if (extensions.length > 0 && extensions[0].getClass().equals(CustomizableLogger.LogLevel.class)){
            return (CustomizableLogger.LogLevel) extensions[0];
        }
        return CustomizableLogger.LogLevel.UNKNOWN;
    }

    private String getTypeName(Object[] extension) {
        if (extension.length > 1 && extension[1].getClass().equals(Class.class)){
            return ((Class<?>) extension[1]).getSimpleName();
        }
        return "";
    }
}
