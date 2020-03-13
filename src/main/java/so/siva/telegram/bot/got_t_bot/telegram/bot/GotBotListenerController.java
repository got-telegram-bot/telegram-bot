package so.siva.telegram.bot.got_t_bot.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.aop.LooperHack;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.DefaultCommandRegistryConsumer;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled.UpdateHandlerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GotBotListenerController extends TelegramLongPollingCommandBot {

    private final String botToken;
    private final String botUserName;

    @Autowired
    private LooperHack looperHack;

    @Autowired
    private UpdateHandlerFactory updateHandlerFactory;

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
        //Оборачиваем обработку для АОП
        //Если начата запись поста, то сообщения складываются в базу
        update = looperHack.apply(update);
        if (update == null){
            logger.warn("Update was null");
            return;
        }
        try{
            if (update.hasMessage()) {
                UpdateHandlerFactory.UserHandlerContainer updateHandler = updateHandlerFactory.provideHandler(update.getMessage());
                updateHandler.getHandler().processMessage(update.getMessage(), updateHandler.getUser());
            }
            if (update.hasCallbackQuery()){
                String data = update.getCallbackQuery().getData();

                String[] trimmedData = data.split("\\.");
                String commandIdentifier = trimmedData[0];
                if (getRegisteredCommands().stream().anyMatch(iBotCommand -> iBotCommand.getCommandIdentifier().equals(commandIdentifier))){
                    List<String> arguments = Arrays.stream(trimmedData).skip(1).collect(Collectors.toList());
                    arguments.add(update.getCallbackQuery().getMessage().getMessageId().toString());

                    IBotCommand command = getRegisteredCommand(commandIdentifier);
                    command.processMessage(this, update.getCallbackQuery().getMessage(), arguments.toArray(new String[0]));
                }
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
