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
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.List;

public abstract class ACommand extends BotCommand {

    protected final boolean isAdminCommand;

    private Logger logger = LoggerFactory.getLogger(ACommand.class);

    public ACommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description);
        gotBotListenerController.register(this);
        this.isAdminCommand = true;
    }

    public ACommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController, boolean isAdminCommand) {
        super(commandIdentifier, description);
        gotBotListenerController.register(this);
        this.isAdminCommand = isAdminCommand;
    }


    public void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(AbsSender sender, SendPhoto photo, User user) {
        try {
            sender.execute(photo);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(AbsSender sender, DeleteMessage deleteMessage, User user) {
        try {
            sender.execute(deleteMessage);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(AbsSender sender, EditMessageReplyMarkup replyMarkup, User user) {
        try {
            sender.execute(replyMarkup);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }
    public void execute(AbsSender sender, EditMessageText messageText, User user) {
        try {
            sender.execute(messageText);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(AbsSender sender, SendMediaGroup sendMediaGroup, User user) {
        try {
            sender.execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    public void execute(AbsSender sender, EditMessageMedia editMessageMedia, User user) {
        try {
            sender.execute(editMessageMedia);
        } catch (TelegramApiException e) {
            logger.error(user.getId() + " " + getCommandIdentifier() + " " + e.getMessage());
        }
    }

    protected SendMessage prepareSendMessage(String message, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
    }

    protected InputMediaPhoto prepareInputMediaPhoto(String fileId, String caption){
        InputMediaPhoto sendPhoto = new InputMediaPhoto();
        sendPhoto.setMedia(fileId);
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    protected SendPhoto prepareSendPhoto(String fileId, String caption, String chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public boolean isAdminCommand() {
        return this.isAdminCommand;
    }

    //    protected SendMediaGroup prepareMediaGroup(List<InputMedia> photos, String chatId){
//        SendMediaGroup sendMediaGroup = new SendMediaGroup();
//        sendMediaGroup.setChatId(chatId);
//        sendMediaGroup.setMedia(photos);
//        return sendMediaGroup;
//    }

}
