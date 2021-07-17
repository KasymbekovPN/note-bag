package ru.kpn.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomizableLoggerExtender implements LoggerExtender<CustomizableLogger.LogLevel> {

    @Override
    public Object[] extendArgs(Object[] args, Object... extension) {
        Thread thread = Thread.currentThread();
        int size = args.length + 5;
        Object[] extendedArgs = new Object[size];
        extendedArgs[0] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        extendedArgs[1] = getLogLevel(extension);
        extendedArgs[2] = thread.getId();
        extendedArgs[3] = thread.getName();
        extendedArgs[4] = getTypeName(extension);

        System.arraycopy(args, 0, extendedArgs, 5, args.length);

        return extendedArgs;
    }

    @Override
    public String extendTemplate(String template) {
        return "[{}] [{}] [{}] [{}] [{}] : " + template;
    }

    private CustomizableLogger.LogLevel getLogLevel(Object[] extension) {
        if (extension.length > 0 && extension[0].getClass().equals(CustomizableLogger.LogLevel.class)){
            return (CustomizableLogger.LogLevel) extension[0];
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
