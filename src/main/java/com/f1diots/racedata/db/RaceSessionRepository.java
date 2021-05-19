package com.f1diots.racedata.db;

import com.f1diots.racedata.db.model.RaceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface RaceSessionRepository extends JpaRepository<RaceSession, String> {

    @Query(value="SELECT DISTINCT session.id FROM session", nativeQuery = true)
    Collection<String> findAllIds();

}
