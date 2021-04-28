package com.f1diots.racedata.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "session")
public class RaceSession {
    @Id
    private String id;
    private Instant timestamp;
    private String trackName;
    private String sessionType;
    private boolean wet;
    @OneToMany
    private List<LeaderBoardLine> leaderBoardLines;
}