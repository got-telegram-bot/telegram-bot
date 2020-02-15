package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.IBotUpdateHandler;

@Controller
public class StickerHandler implements IBotUpdateHandler {

    @Override
    public Object handleIncomingMessage(Update update) {
        //Извлекаем объект входящего сообщения
        Message inMessage = update.getMessage();

        System.out.println(""
                + inMessage.getChat().getFirstName()
                + " "
                + inMessage.getChat().getLastName()
                + ": "
                + inMessage.getChat().getStickerSetName());

        //Создаем исходящее сообщение
        SendSticker outMessage = new SendSticker();
        //Указываем в какой чат будем отправлять сообщение
        //(в тот же чат, откуда пришло входящее сообщение)

        outMessage.setChatId(inMessage.getChatId());

//                Long inChatId = inMessage.getChatId();
//                if (inChatId.equals(chatIdDrenal) || inChatId.equals(chatIdSiva)){
//                    outMessage.setChatId(chatIdRainbow);
//                }
//                if (inChatId.equals(chatIdRainbow)){
//                    outMessage.setChatId(chatIdDrenal);
//                }


        //Указываем текст сообщения
        return outMessage.setSticker(inMessage.getSticker().getFileId());
        //Отправляем сообщение
//        execute(outMessage);
    }
}
