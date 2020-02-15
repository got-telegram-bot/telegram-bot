package so.siva.telegram.bot.got_t_bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(GotBotListenerController gotBotListenerController){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(gotBotListenerController);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

        return telegramBotsApi;
    }

}
