package ru.kpn.creator;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kpn.matcher.MatcherFactory;
import ru.kpn.matcher.MatcherType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
@ConfigurationProperties(prefix = "telegram.tube")
public class StrategyMatcherCreator {

    private final Map<String, Result> results = new HashMap<>();

    private MatcherFactory<Update, Boolean> factory;
    private Map<String, MatcherData> contentMatchers;

    @Autowired
    public void setFactory(MatcherFactory<Update, Boolean> factory) {
        this.factory = factory;
    }

    public void setContentMatchers(Map<String, MatcherData> contentMatchers) {
        this.contentMatchers = contentMatchers;
    }

    public Result getOrCreate(String name){
        if (results.containsKey(name)){
            return results.get(name);
        }

        Result newResult = attemptCreateNewMatcher(name);
        results.put(name, newResult);

        return newResult;
    }

    private Result attemptCreateNewMatcher(String name) {
        MatcherBuilder matcherBuilder = new MatcherBuilder();
        return matcherBuilder
                .name(name)
                .matcherData(contentMatchers.getOrDefault(name, null))
                .factory(factory)
                .checkMatcherDataExistence()
                .prepareMatcherType()
                .checkAndPrepareArgs()
                .build();
    }

    @Setter
    @Getter
    public static class MatcherData {
        private String type;
        private Boolean constant;
        private String template;
        private Set<String> templates;
    }

    @Builder
    @Getter
    public static class Result{
        private Boolean success;
        private Function<Update, Boolean> matcher;
        private String errorMessage;
    }

    @Getter
    private static class MatcherBuilder {
        private MatcherData matcherData;
        private String name;
        private MatcherFactory<Update, Boolean> factory;

        private Boolean success = true;
        private MatcherType matcherType;
        private Object[] args;

        // TODO: 02.11.2021 StrategyCalculatorSource!!!
        private String errorMessage = "";

        public MatcherBuilder name(String name){
            this.name = name;
            return this;
        }

        public MatcherBuilder matcherData(MatcherData matcherData){
            this.matcherData = matcherData;
            return this;
        }

        public MatcherBuilder factory(MatcherFactory<Update, Boolean> factory){
            this.factory = factory;
            return this;
        }

        public MatcherBuilder checkMatcherDataExistence(){
            if (success && matcherData == null){
                success = false;
                errorMessage = String.format("Matcher data for '%s' doesn't exist", name);
            }

            return this;
        }

        public MatcherBuilder prepareMatcherType(){
            if (success){
                // TODO: 02.11.2021 move to enum
                if (matcherData.getType() != null){
                    try{
                        matcherType = MatcherType.valueOf(matcherData.getType());
                    } catch (IllegalArgumentException ex){
                        success = false;
                        errorMessage = String.format("Type '%s' is invalid [%s]", matcherData.getType(), name);
                    }
                } else {
                    success = false;
                    errorMessage = String.format("Type for '%s' is null", name);
                }
            }
            return this;
        }

        // TODO: 02.11.2021 move to enum MatcherType 
        public MatcherBuilder checkAndPrepareArgs(){
            if (success){
                if (matcherType.equals(MatcherType.CONSTANT)){
                    if (matcherData.getConstant() != null){
                        args = new Object[]{matcherData.getConstant()};
                    } else {
                        success = false;
                        errorMessage = String.format("Invalid arg 'constant' [%s]", name);
                    }
                } else if (matcherType.equals(MatcherType.REGEX)){
                    if (matcherData.getTemplate() != null){
                        args = new Object[]{matcherData.getTemplate()};
                    } else {
                        success = false;
                        errorMessage = String.format("Invalid arg 'template' [%s]", name);
                    }
                } else if (matcherType.equals(MatcherType.MULTI_REGEX)){
                    if (matcherData.getTemplates() != null){
                        args = matcherData.getTemplates().toArray();
                    } else {
                        success = false;
                        errorMessage = String.format("Invalid arg 'templates' [%s]", name);
                    }
                }
            }
            return this;
        }

        public Result build(){
            Result.ResultBuilder builder = Result.builder().success(this.success).errorMessage(errorMessage);
            if (success){
                builder.matcher(factory.create(matcherType, args));
            }
            return builder.build();
        }
    }
}
