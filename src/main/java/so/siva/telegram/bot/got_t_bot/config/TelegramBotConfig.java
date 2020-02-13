package so.siva.telegram.bot.got_t_bot.config;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotHandler;

@Configuration
public class TelegramBotConfig {

    static {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new GotBotHandler());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }




}
