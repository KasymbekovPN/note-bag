package ru.kpn.service.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.logging.LoggerSettings;

@Service
public class LoggerServiceImpl implements LoggerService<CustomizableLogger.LogLevel> {

    @Autowired
    private LoggerSettings<CustomizableLogger.LogLevel> defaultLoggerSettings;

    @Override
    public Logger<CustomizableLogger.LogLevel> create(Class<?> aClass) {
//        return CustomizableLogger.bui
        //<
        return null;
    }
}
