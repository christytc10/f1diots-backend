package com.f1diots.racedata.task.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class AccCarSerializer extends StdSerializer<AccCar> {
    
    public AccCarSerializer() {
        super(AccCar.class);
    }

    public AccCarSerializer(Class t) {
        super(t);
    }

    public void serialize(
            AccCar distance, JsonGenerator generator, SerializerProvider provider)
      throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("id");
        generator.writeNumber(distance.getId());
        generator.writeFieldName("name");
        generator.writeString(distance.getName());
        generator.writeFieldName("year");
        generator.writeNumber(distance.getYear());
        generator.writeFieldName("carClass");
        generator.writeString(distance.getCarClass());
        generator.writeEndObject();
    }
}