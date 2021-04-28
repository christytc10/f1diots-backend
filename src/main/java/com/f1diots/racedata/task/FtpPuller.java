package com.f1diots.racedata.task;

import com.f1diots.racedata.db.RaceDataRepository;
import com.f1diots.racedata.model.AccCar;
import com.f1diots.racedata.model.RaceData;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Log4j2
public class FtpPuller {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMdd_hhmmss");

    @Value("${race.data.ftp.url}")
    String ftpUrl;
    @Value("${race.data.ftp.port}")
    int defaultPort;
    @Value("${race.data.ftp.user}")
    String user;
    @Value("${race.data.ftp.pass}")
    String pass;
    @Value("${race.data.ftp.remoteDir}")
    String remoteDir;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    RaceDataRepository raceDataDb;

    Set<String> knownIds = new HashSet<>();

    @Scheduled(fixedRate = 60000 * 5) //Every 5 minutes
    public void pullServerResults() {
        if(knownIds.isEmpty()) {
            populateIdCache();
        }
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        log.info("Running scheduled task");
        Map<String, String> raceData = getRaceDataFromFtp();
        raceData.keySet().forEach(k -> {
            RaceData serverRaceData;
            try {
                serverRaceData = mapper.readValue(raceData.get(k), RaceData.class);
                if(!serverRaceData.getLaps().isEmpty()) {
                    serverRaceData.setId(k);
                    serverRaceData.setTimestamp(parseTimestamp(k));
                    raceDataDb.save(serverRaceData).block();
                } else {
                    log.info("Session {} was empty.", k);
                }
                knownIds.add(k);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    private Instant parseTimestamp(String fileId) {
        //Example fileId: 210422_232210_FP
        String dateString = fileId.substring(0, fileId.lastIndexOf("_"));
        return DATE_FORMAT.parse(dateString).toInstant();
    }

    private void populateIdCache() {
        log.info("Refreshing IDs from DB");
        Set<String> idsFromDb = Objects.requireNonNull(raceDataDb.getSessionIds()
                .collectList()
                .block())
                .stream()
                .map(RaceData::getId)
                .collect(Collectors.toSet());
        knownIds.addAll(idsFromDb);
    }

    private Map<String, String> getRaceDataFromFtp() {
        Map<String, String> raceData = new HashMap<>();
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(ftpUrl, defaultPort);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            FTPFile[] fileList = ftpClient.listFiles(remoteDir);
            for (FTPFile ftpFile : fileList) {

                    String filename = ftpFile.getName();
                    String id = filename.substring(0, filename.lastIndexOf("."));
                    if (!filename.substring(filename.lastIndexOf(".")).equals(".json")) {
                        // ignore any non-json files
                        continue;
                    }
                    if(knownIds.contains(id)){
                        log.info("{} already in DB", id);
                        continue;
                    }
                    log.info("{} not in DB, saving...", id);
                try {
                    InputStream stream = ftpClient.retrieveFileStream(remoteDir + filename);
                    String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining(""));
                    raceData.put(id, result.replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", ""));
                } finally {
                    ftpClient.completePendingCommand();
                }
            }
        } catch (Exception e) {
            log.info("Error in scheduled task", e);
        }
        return raceData;
    }
}