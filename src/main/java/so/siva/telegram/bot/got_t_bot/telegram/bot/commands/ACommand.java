package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;


public abstract class ACommand extends BotCommand {

    protected final AbsSender sender;

    protected final boolean isAdminCommand;

    private Logger logger = LoggerFactory.getLogger(ACommand.class);

    public ACommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description);
        gotBotListenerController.register(this);
        this.sender = gotBotListenerController;
        this.isAdminCommand = true;
    }

    public ACommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController, boolean isAdminCommand) {
        super(commandIdentifier, description);
        gotBotListenerController.register(this);
        this.sender = gotBotListenerController;
        this.isAdminCommand = isAdminCommand;
    }


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

    protected SendMessage prepareSendMessage(String message, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    protected SendMessage prepareSendMessage(String message, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    protected InputMediaPhoto prepareInputMediaPhoto(String fileId, String caption){
        InputMediaPhoto sendPhoto = new InputMediaPhoto();
        sendPhoto.setMedia(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    protected SendPhoto prepareSendPhoto(String fileId, String caption, String chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode("HTML");
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
