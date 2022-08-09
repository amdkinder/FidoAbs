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
public enum PaymentType {
    UZCARD("22"),
    HUMO("37"),
    CASH("30");

    private final String code;

    public static PaymentType getByCode(String code) {
        if (code == null) {
            return null;
        }
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(null);
    }

    public static class PaymentTypeSerializer extends JsonSerializer<PaymentType> {
        @Override
        public void serialize(PaymentType paymentType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (paymentType != null) {
                jsonGenerator.writeString(paymentType.getCode());
            }
        }
    }

    public static class PaymentTypeDeSerializer extends JsonDeserializer<PaymentType> {
        @Override
        public PaymentType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
            return PaymentType.getByCode(jsonParser.getText());
        }
    }


}
