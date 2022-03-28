# F1diots backend

The website is deployed at [f1diots.com](https://f1diots.com). It's tracking race sessions from our community server on Assetto Corsa Competizione and showing the best lap times.

## Endpoints

####  Get sessions
```/raceData?pageSize=2&page=0```
page is 0 indexed. Default sort is by descending timestamp

####  Get specific session
```/raceData/210422_233453_FP```
Pass in the ID from the raceData. Returns the (almost) full results payload



```sql
DELETE FROM session_leaderboard_line_laps WHERE laps_id IN (SELECT id FROM lap WHERE valid_for_best=False);
DELETE FROM lap WHERE valid_for_best=False;

DELETE FROM session_leaderboard_line_laps WHERE laps_id IN (SELECT id FROM lap WHERE lap_time NOT IN (SELECT MIN(lap.lap_time) 
FROM lap 
JOIN session_leaderboard_line_laps slbll ON slbll.laps_id = lap.id
JOIN session on session.id = slbll.leader_board_line_session_id
WHERE lap.valid_for_best = true									 
GROUP BY lap.driver_player_id, session.track_name
ORDER BY lap.driver_player_id, session.track_name));


DELETE FROM lap WHERE lap_time NOT IN (SELECT MIN(lap.lap_time) 
FROM lap 
JOIN session_leaderboard_line_laps slbll ON slbll.laps_id = lap.id
JOIN session on session.id = slbll.leader_board_line_session_id
WHERE lap.valid_for_best = true									 
GROUP BY lap.driver_player_id, session.track_name
ORDER BY lap.driver_player_id, session.track_name);
```
