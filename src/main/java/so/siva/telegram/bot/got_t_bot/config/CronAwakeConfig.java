package so.siva.telegram.bot.got_t_bot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class CronAwakeConfig {

    @Value("${awake.server.url}")
    private String pingUrlPath;

    public CronAwakeConfig() {
        logger.warn("Ping bean has been created");
    }

    private Logger logger = LoggerFactory.getLogger(CronAwakeConfig.class);

    @Scheduled(cron = "0 0/31 8-20 * * ?")
    public void scheduledPingToAwakeApp(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(pingUrlPath + "/admin/ping", String.class);
        logger.info("scheduled cron get:" +  responseEntity.getBody());
    }
}
