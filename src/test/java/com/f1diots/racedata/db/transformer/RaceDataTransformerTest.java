package com.f1diots.racedata.db.transformer;

import com.f1diots.racedata.db.model.RaceSession;
import com.f1diots.racedata.task.model.RaceData;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(JUnitPlatform.class)
class RaceDataTransformerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach(){
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
    }

    @Test
    public void testTransform() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File testFile = new File(Objects.requireNonNull(classLoader.getResource("testracedata.json")).getFile());

        RaceData raceData = objectMapper.readValue(testFile, RaceData.class);
        RaceSession sessionData = RaceDataTransformer.transform(raceData);

        String objString = objectMapper.writeValueAsString(sessionData);
        assertThat(sessionData.getLeaderBoardLines(), hasSize(12));
    }

}