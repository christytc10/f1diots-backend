package com.f1diots.racedata.db;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.f1diots.racedata.model.RaceData;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RaceDataRepository extends ReactiveCosmosRepository<RaceData, String> {
    @Query(value = "SELECT c.id from c")
    Flux<RaceData> getSessionIds();

    @Query(value = "SELECT c.id, c.timestamp, c.trackName, c.sessionType from c " +
            "WHERE ARRAY_LENGTH(c.laps) > 0 " +
            "ORDER by c.timestamp " +
            "DESC OFFSET @offset " +
            "LIMIT @limit")
    Flux<RaceData> getSessions(@Param("limit") int limit, @Param("offset") int offset);

    //find sessions (id, track, type) ordered by desc timestamp, offset
}
