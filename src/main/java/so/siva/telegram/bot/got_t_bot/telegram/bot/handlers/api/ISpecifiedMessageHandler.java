package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;

public interface ISpecifiedMessageHandler {
    void processMessage(Message message, GUser currentUser) throws TelegramApiException;
}
