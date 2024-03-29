package de.jkarthaus.posBuddy.service.impl;

import de.jkarthaus.posBuddy.service.FtpSyncService;
import io.micronaut.context.annotation.Value;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.HashMap;

@Singleton
@Slf4j
public class FtpSyncImpl implements FtpSyncService {

    public final String FTP_CONFIG_FILE = "posBuddyFtpConfig.json";

    @Value("${posbuddy.configDir:/data/posbuddy}")
    private Path configPath;

    private HashMap<String, Integer> lastUploadHashValue;

    private ftoConfigRecord ftpConfigRecord;

    private boolean isConfigValid = false;

    private String lastLog;

    @Override
    public void putFtpServerConfig(ftoConfigRecord ftoConfigRecord) {

    }

    @Override
    @PostConstruct
    public ftoConfigRecord getFtpServerConfig() {
        log.info("Try to read ftp Server config from File:{}", configPath.resolve(FTP_CONFIG_FILE));
        if (!configPath.resolve(FTP_CONFIG_FILE).toFile().exists()) {
            log.warn("No FTP configuration found - cannot create static guest data.");
            isConfigValid = false;
            this.lastLog = """
                    **ERROR**  
                     No FTP configuration found - _cannot create static guest data._
                    """;
        }
        isConfigValid = true;

        return null;
    }

    @Scheduled(fixedDelay = "60s", initialDelay = "5s")
    @Override
    public void startSync() {
        log.info("Start FTP Snyc...");
        if (ftpConfigRecord == null) {
            log.warn("Ftp config not set.");
            return;
        }

    }

    @Override
    public String getLastLog() {
        return lastLog;
    }
}
