package so.siva.telegram.bot.got_t_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotHandler;

@SpringBootApplication
public class GotTBotApplication {

	public static void main(String[] args) {
        ApiContextInitializer.init();
//        TelegramBotsApi botsApi = new TelegramBotsApi();
//        try {
//            botsApi.registerBot(new GotBotHandler());
//        } catch (TelegramApiRequestException e) {
//            e.printStackTrace();
//        }


		SpringApplication.run(GotTBotApplication.class, args);
	}

}
