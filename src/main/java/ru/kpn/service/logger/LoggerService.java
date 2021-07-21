package ru.kpn.service.logger;

import ru.kpn.logging.Logger;

public interface LoggerService<L> {
    Logger<L> create(Class<?> aClass);
}
