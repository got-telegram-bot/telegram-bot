package so.siva.telegram.bot.got_t_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class GotTBotApplication {

	public static void main(String[] args) {
        ApiContextInitializer.init();

		SpringApplication.run(GotTBotApplication.class, args);
	}

}
