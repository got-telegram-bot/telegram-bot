package so.siva.telegram.bot.got_t_bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotHandler;

@Configuration
public class TelegramBotConfig {

    @Autowired
    private GotBotHandler gotBotHandler;

    @Bean
    public TelegramBotsApi telegramBotsApi(){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(gotBotHandler);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        return telegramBotsApi;
    }

}
