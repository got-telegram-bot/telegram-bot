package so.siva.telegram.bot.got_t_bot.telegram.bot.builders;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.InlineMarkupBuilder.*;

public class GeneralResponseBuilder {

    public static final String DEFAULT_AUTO_CLOSABLE_LABEL = "Ok ❎";

    public static SendMessage prepareAutoClosableMessage(String message, Long chatId){
        return prepareAutoClosableMessage(message, DEFAULT_AUTO_CLOSABLE_LABEL, chatId);
    }

    public static SendMessage prepareAutoClosableMessage(String message, Chat chat){
        return prepareAutoClosableMessage(message, DEFAULT_AUTO_CLOSABLE_LABEL, chat);
    }

    public static SendMessage prepareAutoClosableMessage(String message, String label, Long chatId){
        return prepareSendMessage(message, chatId).setReplyMarkup(createAutoClosableMarkup(label));
    }

    public static SendMessage prepareAutoClosableMessage(String message, String label, Chat chat){
        return prepareSendMessage(message, chat).setReplyMarkup(createAutoClosableMarkup(label));
    }


    public static SendMessage prepareSendMessage(String message, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    public static SendMessage prepareSendMessage(String message, Chat chat){
        return prepareSendMessage(message, chat.getId());
    }

    public static InputMediaPhoto prepareInputMediaPhoto(String fileId, String caption){
        InputMediaPhoto sendPhoto = new InputMediaPhoto();
        sendPhoto.setMedia(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public static SendPhoto prepareSendPhoto(String fileId, String caption, Chat chat){
        return prepareSendPhoto(fileId, caption, chat.getId());
    }

    public static SendPhoto prepareSendPhoto(String fileId, String caption, Long chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public static SendPhoto prepareSendPhoto(String fileId, Chat chat){
        return prepareSendPhoto(fileId, chat.getId());
    }

    public static SendPhoto prepareSendPhoto(String fileId, Long chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        return sendPhoto;
    }
}
