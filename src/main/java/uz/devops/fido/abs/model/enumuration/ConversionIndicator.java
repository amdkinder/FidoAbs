package uz.devops.fido.abs.model.enumuration;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ConversionIndicator {
    CONVERSE("CR"), REVERSE("DR");

    private final String code;

    public static ConversionIndicator getByCode(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(null);
    }

    public static class ConIndicatorSerializer extends JsonSerializer<ConversionIndicator> {
        @Override
        public void serialize(ConversionIndicator conversionIndicator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (conversionIndicator != null) {
                jsonGenerator.writeString(conversionIndicator.getCode());
            }
        }
    }

    public static class ConIndicatorDeserializer extends JsonDeserializer<ConversionIndicator> {

        @Override
        public ConversionIndicator deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return ConversionIndicator.getByCode(jsonParser.getText());
        }
    }

}
