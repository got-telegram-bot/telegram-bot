package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface IBotUpdateHandler {
    Object handleIncomingMessage(Update update) throws TelegramApiException;
}
