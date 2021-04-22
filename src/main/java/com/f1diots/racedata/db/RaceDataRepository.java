package com.f1diots.racedata.db;

import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.f1diots.racedata.model.RaceData;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceDataRepository extends ReactiveCosmosRepository<RaceData, String> {
}
