package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.InlineMarkupBuilder.AUTO_CLOSABLE_CALLBACK;

@Component
public class CallbackQueryHandler {

    @Autowired
    private AbsSender absSender;

    private Logger logger = LoggerFactory.getLogger(CallbackQueryHandler.class);


    public void processCallbackQuery(CallbackQuery callbackQuery, Collection<IBotCommand> registeredCommands) throws TelegramApiException {
        String data = callbackQuery.getData();
        Message message = callbackQuery.getMessage();

        String[] trimmedData = data.split("\\.");
        String commandIdentifier = trimmedData[0];
        if (Arrays.asList(trimmedData).contains(AUTO_CLOSABLE_CALLBACK)){
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(message.getChat().getId());
            deleteMessage.setMessageId(message.getMessageId());
            absSender.execute(deleteMessage);
            return;
        }
        if (registeredCommands.stream().anyMatch(iBotCommand -> iBotCommand.getCommandIdentifier().equals(commandIdentifier))){
            List<String> arguments = Arrays.stream(trimmedData).skip(1).collect(Collectors.toList());
            arguments.add(message.getMessageId().toString());

            IBotCommand command = registeredCommands.stream().filter(botCommand -> botCommand.getCommandIdentifier().equals(commandIdentifier)).findFirst().orElseThrow(() -> new IllegalArgumentException("Command is not found"));
            command.processMessage(absSender, message, arguments.toArray(new String[0]));
        }

    }
}
