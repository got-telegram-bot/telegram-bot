package so.siva.telegram.bot.got_t_bot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;

@Component
public class CronAwakeConfig {

    @Autowired
    private AbsSender absSender;

    private Logger logger = LoggerFactory.getLogger(CronAwakeConfig.class);

    @Scheduled(cron = "0 0/1 8-15 * * ?")
    public void scheduledPingToAwakeApp(){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Ping " + new Date().toString());
        sendMessage.setChatId("381855899");
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
        logger.info("-----------Ping " + new Date().toString());
    }
}
