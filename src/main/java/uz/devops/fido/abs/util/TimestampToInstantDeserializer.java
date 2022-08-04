package uz.devops.fido.abs.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class TimestampToInstantDeserializer extends JsonDeserializer<Instant> {

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var text = jsonParser.getText();
        return Instant.EPOCH.plus(Duration.ofSeconds(Long.parseLong(text)));
    }
}
