package ru.kpn.extractor;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.EnumMap;
import java.util.function.Function;

@AllArgsConstructor
public class ExtractorFactoryImpl implements ExtractorFactory<Update, String> {

    private final EnumMap<ExtractorType, Function<Object[], Function<Update, String>>> creators;

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public Function<Update, String> create(ExtractorType type, Object... args) {
        return creators.get(type).apply(args);
    }

    public static class Builder {
        private final EnumMap<ExtractorType, Function<Object[], Function<Update, String>>> creators
                = new EnumMap<>(ExtractorType.class);

        public Builder creator(ExtractorType type, Function<Object[], Function<Update, String>> creator){
            creators.put(type, creator);
            return this;
        }

        public ExtractorFactoryImpl build() throws Exception {
            checkCreatorsAmount();
            return new ExtractorFactoryImpl(creators);
        }

        private void checkCreatorsAmount() throws Exception {
            if (creators.size() != ExtractorType.values().length){
                // TODO: 03.11.2021 exception with message source
                throw new Exception("Extractor creators aren't set completely");
            }
        }
    }
}
