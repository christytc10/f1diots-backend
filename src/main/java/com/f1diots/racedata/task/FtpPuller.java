package com.f1diots.racedata.task;

import com.f1diots.racedata.db.RaceDataRepository;
import com.f1diots.racedata.model.RaceData;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Log4j2
public class FtpPuller {

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

    public void pullServerResults() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        log.info("Running scheduled task");
        Map<String, String> raceData = getRaceDataFromFtp();
        raceData.keySet().forEach(k -> {
            RaceData serverRaceData;
            try {
                serverRaceData = mapper.readValue(raceData.get(k), RaceData.class);
                serverRaceData.setId(k);
                RaceData savedUserMono = raceDataDb.save(serverRaceData).block();
                log.info(savedUserMono);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
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
                try {
                    String filename = ftpFile.getName();
                    if (!filename.substring(filename.lastIndexOf(".")).equals(".json")) {
                        // ignore any non-json files
                        continue;
                    }
                    InputStream stream = ftpClient.retrieveFileStream(remoteDir + filename);
                    String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining(""));
                    String id = filename.substring(0, filename.lastIndexOf("."));
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