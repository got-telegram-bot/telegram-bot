package so.siva.telegram.bot.got_t_bot.telegram.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class GotBotListenerController extends TelegramLongPollingBot {

    private final String botToken;
    private final String botUserName;

    public GotBotListenerController(@Value("${telegram.bot.token}") String botToken, @Value("${telegram.bot.username}") String botUserName) {
        this.botToken = botToken;
        this.botUserName = botUserName;
    }

    private final Long chatIdDrenal = 427924506L;
    private final Long chatIdRainbow = 416724770L;
    private final Long chatIdSiva = 381855899L;

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Object sendObject = null;
        try{
            if (update.hasMessage() && update.getMessage().hasText()) {

            }
            if (update.hasMessage() && update.getMessage().hasSticker()) {

            }
//            execute();

        }catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return this.botUserName;
    }

}
