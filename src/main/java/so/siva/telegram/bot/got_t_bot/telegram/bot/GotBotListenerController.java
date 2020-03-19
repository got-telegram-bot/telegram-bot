package so.siva.telegram.bot.got_t_bot.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.DefaultCommandRegistryConsumer;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.callback.CallbackQueryHandler;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled.MessageHandlerFactory;

@Component
public class GotBotListenerController extends TelegramLongPollingCommandBot {

    private final String botToken;
    private final String botUserName;

    @Autowired
    private MessageHandlerFactory messageHandlerFactory;

    @Autowired
    private CallbackQueryHandler callbackQueryHandler;

    private Logger logger = LoggerFactory.getLogger(GotBotListenerController.class);

    public GotBotListenerController(@Value("${telegram.bot.token}") String botToken,
                                    @Value("${telegram.bot.username}") String botUserName,
                                    DefaultCommandRegistryConsumer defaultCommandRegistryConsumer
    ) {
        super(new DefaultBotOptions(), false);
        this.botToken = botToken;
        this.botUserName = botUserName;
        registerDefaultAction(defaultCommandRegistryConsumer);
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        try{
            if (update.hasMessage()) {
                MessageHandlerFactory.UserHandlerContainer updateHandler = messageHandlerFactory.provideHandler(update.getMessage());
                updateHandler.getHandler().processMessage(update.getMessage(), updateHandler.getUser());
            }
            if (update.hasCallbackQuery()){
                callbackQueryHandler.processCallbackQuery(update.getCallbackQuery(), getRegisteredCommands());
            }
        }catch (TelegramApiException e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return this.botUserName;
    }
}
