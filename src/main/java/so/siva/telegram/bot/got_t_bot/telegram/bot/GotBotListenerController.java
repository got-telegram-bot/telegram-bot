package so.siva.telegram.bot.got_t_bot.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.service.AdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.AuthorizeCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.BattleCardsCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.HouseRandomCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post.CancelPostCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post.SendPostCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post.StartPostCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post.ViewPostCommand;
import so.siva.telegram.bot.got_t_bot.web.aop.LooperHack;

@Component
public class GotBotListenerController extends TelegramLongPollingCommandBot {

    private final String botToken;
    private final String botUserName;

    @Autowired
    private LooperHack looperHack;

    private Logger logger = LoggerFactory.getLogger(GotBotListenerController.class);

    public GotBotListenerController(@Value("${telegram.bot.token}") String botToken,
                                    @Value("${telegram.bot.username}") String botUserName
    ) {
        super(new DefaultBotOptions(), false);
        this.botToken = botToken;
        this.botUserName = botUserName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        //Оборачиваем обработку для АОП
        update = looperHack.apply(update);
        if (update == null){
            logger.warn("Update was null");
            return;
        }
        try{
            if (update.hasMessage() && update.getMessage().hasText()) {

                SendMessage answer = new SendMessage();
                answer.setChatId(update.getMessage().getChatId());

                answer.setText(update.getMessage().getText());
                execute(answer);
            }

            if (update.hasMessage() && update.getMessage().hasPhoto()) {

                SendPhoto answer = new SendPhoto();
                answer.setChatId(update.getMessage().getChatId());

                answer.setPhoto(update.getMessage().getPhoto().get(0).getFileId());
                answer.setCaption(update.getMessage().getCaption());
                execute(answer);
            }


        }catch (TelegramApiException e){
            logger.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUserName;
    }

}
