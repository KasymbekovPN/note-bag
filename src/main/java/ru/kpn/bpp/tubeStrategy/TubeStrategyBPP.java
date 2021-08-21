package ru.kpn.bpp.tubeStrategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

// TODO: 18.08.2021 restore
//@Slf4j
//@Component
//public class TubeStrategyBPP implements BeanPostProcessor {
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//
//        log.info(" ??? : {}", beanName);
//        Class<?> type = bean.getClass();
//        final Type[] genericInterfaces = type.getSuperclass().getGenericInterfaces();
//        for (Type genericInterface : genericInterfaces) {
//            log.info(" --- : {}", genericInterface);
//        }
//
////        for (Class<?> anInterface : interfaces) {
////            log.info(" --- : {}", anInterface);
////        }
////        Type[] genericInterfaces = bean.getClass().getGenericInterfaces();
////        for (Type genericInterface : genericInterfaces) {
////            log.info(" --- : {}", genericInterface);
////        }
//
//        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
//    }
//
//    //    @Slf4j
////    @Component
////    public class LoggerBPP implements BeanPostProcessor {
////
////        private final Map<String, LoggerService<?>> loggerServices = new HashMap<>();
////
////        public LoggerBPP(Set<LoggerService<CustomizableLogger.LogLevel>> loggerServices) {
////            loggerServices.forEach(ls -> this.loggerServices.put(ls.getId(), ls));
////        }
////
////        @Override
////        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
////            ru.kpn.bpp.logger.LoggerBPP.InjectionData injectionData = checkBean(bean);
////            injectLoggerIfNeed(injectionData);
////
////            return bean;
////        }
//
//}
