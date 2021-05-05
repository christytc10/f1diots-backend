package com.f1diots.racedata.db;

import com.f1diots.racedata.db.model.RaceSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceSessionRepository extends JpaRepository<RaceSession, String> {
}
