package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.IBotUpdateHandler;

@Controller
public class BotCommandHandler extends DefaultAbsSender implements IBotUpdateHandler {

    protected BotCommandHandler(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public BotApiMethod handleIncomingMessage(Update update) throws TelegramApiException {
        execute(new SendMessage());
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }


}
