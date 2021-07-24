package ru.kpn.service.logger;

import ru.kpn.logging.Logger;
import ru.kpn.logging.LoggerSettings;

public interface LoggerService<L> {
    Logger<L> create(Class<?> type);
    Logger<L> create(Class<?> type, LoggerSettings<L> settings);
}
