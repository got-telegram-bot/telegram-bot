package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.List;

public abstract class ACommand extends BotCommand {

    protected final boolean isAdminCommand;

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
            System.out.println("[ERROR] - command error: " + user.getId() + getCommandIdentifier() +  e.getMessage());
        }
    }

    public void execute(AbsSender sender, SendPhoto photo, User user) {
        try {
            sender.execute(photo);

        } catch (TelegramApiException e) {
            System.out.println("[ERROR] - command error: " + user.getId() + getCommandIdentifier() +  e.getMessage());
        }
    }

    protected SendMessage prepareSendMessage(String message, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
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

//    protected SendMediaGroup prepareMediaGroup(List<InputMedia> photos, String chatId){
//        SendMediaGroup sendMediaGroup = new SendMediaGroup();
//        sendMediaGroup.setChatId(chatId);
//        sendMediaGroup.setMedia(photos);
//        return sendMediaGroup;
//    }

}
