package ru.kpn.bpp;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.service.logger.LoggerService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggerBPP implements BeanPostProcessor {

    private final Map<String, LoggerService<?>> loggerServices = new HashMap<>();

    public LoggerBPP(Set<LoggerService<CustomizableLogger.LogLevel>> loggerServices) {
        loggerServices.forEach(ls -> this.loggerServices.put(ls.getId(), ls));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        InjectionData injectionData = checkBean(bean);
        injectLoggerIfNeed(injectionData);

        return bean;
    }

    @SneakyThrows
    private InjectionData checkBean(Object bean) {
        Class<?> type = bean.getClass();
        InjectionData.InjectionDataBuilder builder = InjectionData.builder()
                .type(type)
                .bean(bean)
                .success(false);

        for (Field declaredField : type.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(InjectLogger.class)){
                String supposedLoggerServiceId = declaredField.getAnnotation(InjectLogger.class).value();
                if (loggerServices.containsKey(supposedLoggerServiceId))
                {
                    builder
                            .loggerServiceId(supposedLoggerServiceId)
                            .field(declaredField)
                            .success(true);
                    break;
                }
                else {
                    throw new BeansException("Logger service with id " + supposedLoggerServiceId + " doesn't exit") {};
                }
            }
        }

        return builder.build();
    }

    @SneakyThrows
    private void injectLoggerIfNeed(InjectionData injectionData) {
        if (injectionData.isSuccess()){
            Object bean = injectionData.getBean();
            Class<?> type = injectionData.getType();
            Field field = injectionData.getField();
            String loggerServiceId = injectionData.getLoggerServiceId();

            Logger<?> logger = loggerServices.get(loggerServiceId).create(type);

            field.setAccessible(true);
            field.set(bean, logger);
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    private static class InjectionData {
        private final boolean success;
        private final Object bean;
        private final Class<?> type;
        private final Field field;
        private final String loggerServiceId;
    }
}
