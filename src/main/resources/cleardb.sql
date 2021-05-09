DROP TABLE session_leaderboard_line_laps;
DROP TABLE lap;
DROP TABLE driver;
DROP TABLE session_leader_board_lines;
DROP TABLE session_leaderboard_line;
DROP TABLE session;


-- get sessions for a player
SELECT DISTINCT session.id FROM driver
JOIN lap ON lap.driver_player_id = driver.player_id
JOIN session_leaderboard_line_laps slll ON slll.laps_id = lap.id
JOIN session ON slll.leader_board_line_session_id = session.id
WHERE first_name = 'Andreas';