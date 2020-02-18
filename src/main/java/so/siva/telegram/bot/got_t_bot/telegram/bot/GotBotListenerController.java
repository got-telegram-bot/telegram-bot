package so.siva.telegram.bot.got_t_bot.telegram.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.AuthorizeCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.BattleCardsCommand;

@Component
public class GotBotListenerController extends TelegramLongPollingCommandBot {

    private final String botToken;
    private final String botUserName;

    public GotBotListenerController(@Value("${telegram.bot.token}") String botToken,
                                    @Value("${telegram.bot.username}") String botUserName,
                                    AuthorizeCommand authorizeCommand,
                                    BattleCardsCommand battleCardsCommand
    ) {
        super(new DefaultBotOptions(), false);
        this.botToken = botToken;
        this.botUserName = botUserName;

        register(authorizeCommand);
        register(battleCardsCommand);
    }

    private final Long chatIdDrenal = 427924506L;
    private final Long chatIdRainbow = 416724770L;
    private final Long chatIdSiva = 381855899L;

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        try{
            if (update.hasMessage() && update.getMessage().hasText()) {

                SendMessage answer = new SendMessage();
                answer.setChatId(update.getMessage().getChatId());

                answer.setText(update.getMessage().getText());
                execute(answer);
            }

        }catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUserName;
    }

}
