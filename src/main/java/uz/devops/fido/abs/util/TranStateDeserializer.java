package uz.devops.fido.abs.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import uz.devops.fido.abs.model.enumuration.AbsTranState;

import java.io.IOException;

public class TranStateDeserializer extends JsonDeserializer<AbsTranState> {

    @Override
    public AbsTranState deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return AbsTranState.getStateFromCode(jsonParser.getText());
    }
}
