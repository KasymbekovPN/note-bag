package ru.kpn.bpp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ru.kpn.logging.CustomizableLogger;
import ru.kpn.logging.Logger;
import ru.kpn.service.logger.LoggerService;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoggerBPP implements BeanPostProcessor {

    @Autowired
    private LoggerService<CustomizableLogger.LogLevel> loggerService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        InjectionData injectionData = checkBean(bean);
        injectLoggerIfNeed(injectionData);

        return bean;
    }

    @SneakyThrows
    private InjectionData checkBean(Object bean) {
        Class<?> type = bean.getClass();
        boolean success = false;
        String fieldName = "";
        if (type.isAnnotationPresent(InjectLogger.class)){
            Set<String> names = Arrays.stream(type.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
            fieldName = type.getDeclaredAnnotation(InjectLogger.class).value();
            success = names.contains(fieldName);
        }

        return new InjectionData(success, bean, type, fieldName);
    }

    @SneakyThrows
    private void injectLoggerIfNeed(InjectionData injectionData) {
        if (injectionData.isSuccess()){
            Object bean = injectionData.getBean();
            Class<?> type = injectionData.getType();
            String fieldName = injectionData.getFieldName();
            Logger<CustomizableLogger.LogLevel> logger = createLogger(type);

            Field declaredField = type.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(bean, logger);
        }
    }

    private Logger<CustomizableLogger.LogLevel> createLogger(Class<?> type) {
        return loggerService.create(type);
    }

    @Getter
    @AllArgsConstructor
    private static class InjectionData {
        private final boolean success;
        private final Object bean;
        private final Class<?> type;
        private final String fieldName;
    }
}
