package so.siva.telegram.bot.got_t_bot.telegram.bot.builders;


import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.InlineMarkupBuilder.*;

public class GeneralMessageBuilder {

    public static final String DEFAULT_AUTO_CLOSABLE_LABEL = "Ok ❎";
    public static final String HTML_PARSE_MODE = "HTML";

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


    public static DeleteMessage prepareDeleteMessage(Chat chat, Integer messageId){
        return new DeleteMessage().setChatId(chat.getId()).setMessageId(messageId);
    }

    public static DeleteMessage prepareDeleteMessage(Long chatId, Integer messageId){
        return new DeleteMessage().setChatId(chatId).setMessageId(messageId);
    }

    public static SendMessage prepareSendMessage(String message, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode(HTML_PARSE_MODE);
        return sendMessage;
    }

    public static SendMessage prepareSendMessage(String message, Chat chat){
        return prepareSendMessage(message, chat.getId());
    }

    public static InputMediaPhoto prepareInputMediaPhoto(String fileId, String caption){
        InputMediaPhoto sendPhoto = new InputMediaPhoto();
        sendPhoto.setMedia(fileId);
        sendPhoto.setParseMode(HTML_PARSE_MODE);
        if (!StringUtils.isEmpty(caption)){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public static InputMediaPhoto prepareInputMediaPhoto(String fileId){
        return prepareInputMediaPhoto(fileId, null);
    }

    public static SendPhoto prepareSendPhoto(String fileId, String caption, Long chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode(HTML_PARSE_MODE);
        if (!StringUtils.isEmpty(caption)){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public static SendPhoto prepareSendPhoto(String fileId, String caption, Chat chat){
        return prepareSendPhoto(fileId, caption, chat.getId());
    }

    public static SendPhoto prepareSendPhoto(String fileId, Chat chat){
        return prepareSendPhoto(fileId, chat.getId());
    }

    public static SendPhoto prepareSendPhoto(String fileId, Long chatId){
        return prepareSendPhoto(fileId, null, chatId);
    }

    public static EditMessageText prepareEditMessageText(String newMessage, Long chatId, Integer messageId){
        return new EditMessageText().setText(newMessage).setChatId(chatId).setMessageId(messageId).setParseMode(HTML_PARSE_MODE);
    }

    public static EditMessageText prepareEditMessageText(String newMessage, Chat chat, Integer messageId){
        return prepareEditMessageText(newMessage, chat.getId(), messageId);
    }

    public static EditMessageMedia prepareEditMessagePhoto(InputMediaPhoto inputMediaPhoto, Chat chat, Integer messageId){
        return prepareEditMessagePhoto(inputMediaPhoto, chat.getId(), messageId);
    }

    public static EditMessageMedia prepareEditMessagePhoto(InputMediaPhoto inputMediaPhoto, Long chatId, Integer messageId){
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setMedia(inputMediaPhoto);
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(messageId);
        return editMessageMedia;
    }
}
