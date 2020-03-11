package so.siva.telegram.bot.got_t_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;
import so.siva.telegram.bot.got_t_bot.config.InfoHouseCardsCommandsConfig;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties({InfoHouseCardsCommandsConfig.class})
@PropertySource(value = "classpath:application-info_config.yml")
public class GotTBotApplication {

	public static void main(String[] args) {
        ApiContextInitializer.init();
		SpringApplication.run(GotTBotApplication.class, args);
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Minsk"));
	}
}
