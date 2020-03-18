package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.producers.GeneralResponseProducer;
import so.siva.telegram.bot.got_t_bot.telegram.bot.producers.ReplyMarkupProducer;


public abstract class ACommand extends BotCommand {

    @Autowired
    protected GeneralResponseProducer responseProducer;

    @Autowired
    protected ReplyMarkupProducer markupProducer;

    protected final AbsSender sender;

    private Logger logger = LoggerFactory.getLogger(ACommand.class);

    public ACommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description);
        gotBotListenerController.register(this);
        this.sender = gotBotListenerController;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        execute(chat, arguments);
    }

    public abstract void execute(Chat chat, String[] arguments);

    public void execute(SendMessage message) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            logger.error(message.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(SendPhoto photo) {
        try {
            sender.execute(photo);
        } catch (TelegramApiException e) {
            logger.error(photo.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(DeleteMessage deleteMessage) {
        try {
            sender.execute(deleteMessage);
        } catch (TelegramApiException e) {
            logger.error(deleteMessage.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(EditMessageReplyMarkup replyMarkup) {
        try {
            sender.execute(replyMarkup);
        } catch (TelegramApiException e) {
            logger.error(replyMarkup.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }
    public void execute(EditMessageText messageText) {
        try {
            sender.execute(messageText);
        } catch (TelegramApiException e) {
            logger.error(messageText.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(SendMediaGroup sendMediaGroup) {
        try {
            sender.execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            logger.error(sendMediaGroup.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(EditMessageMedia editMessageMedia) {
        try {
            sender.execute(editMessageMedia);
        } catch (TelegramApiException e) {
            logger.error(editMessageMedia.toString() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

}
