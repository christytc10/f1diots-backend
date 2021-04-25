package com.f1diots.racedata.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class AccCarDeserializer extends StdDeserializer<AccCar> {

    public AccCarDeserializer() {
        super(AccCar.class);
    }

    @Override
    public AccCar deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = node.get("id").asInt();
        return AccCar.byId(id);
    }
}