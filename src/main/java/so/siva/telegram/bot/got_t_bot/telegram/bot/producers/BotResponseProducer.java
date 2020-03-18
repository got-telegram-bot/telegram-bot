package so.siva.telegram.bot.got_t_bot.telegram.bot.producers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

@Component
public class BotResponseProducer {

    public SendMessage prepareSendMessage(String message, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    public SendMessage prepareSendMessage(String message, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    public SendMessage prepareSendMessage(String message, Chat chat){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat.getId());
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");
        return sendMessage;
    }

    public InputMediaPhoto prepareInputMediaPhoto(String fileId, String caption){
        InputMediaPhoto sendPhoto = new InputMediaPhoto();
        sendPhoto.setMedia(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public SendPhoto prepareSendPhoto(String fileId, String caption, String chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public SendPhoto prepareSendPhoto(String fileId, String caption, Chat chat){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chat.getId());
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public SendPhoto prepareSendPhoto(String fileId, String caption, Long chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode("HTML");
        if (caption!=null){
            sendPhoto.setCaption(caption);
        }
        return sendPhoto;
    }

    public SendPhoto prepareSendPhoto(String fileId, String chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        sendPhoto.setParseMode("HTML");
        return sendPhoto;
    }

    public SendPhoto prepareSendPhoto(String fileId, Chat chat){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chat.getId());
        sendPhoto.setPhoto(fileId);
        return sendPhoto;
    }

    public SendPhoto prepareSendPhoto(String fileId, Long chatId){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(fileId);
        return sendPhoto;
    }
    //    public SendMediaGroup prepareMediaGroup(List<InputMedia> photos, String chatId){
//        SendMediaGroup sendMediaGroup = new SendMediaGroup();
//        sendMediaGroup.setChatId(chatId);
//        sendMediaGroup.setMedia(photos);
//        return sendMediaGroup;
//    }

}
