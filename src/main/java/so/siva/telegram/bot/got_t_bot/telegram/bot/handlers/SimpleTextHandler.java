package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.IBotUpdateHandler;

@Controller
public class SimpleTextHandler implements IBotUpdateHandler {

    @Override
    public Object handleIncomingMessage(Update update) {
        //Извлекаем объект входящего сообщения
        Message inMessage = update.getMessage();

        System.out.println(""
                + inMessage.getChat().getFirstName()
                + " "
                + inMessage.getChat().getLastName()
                + ": "
                + inMessage.getText());

        //Создаем исходящее сообщение
        SendMessage outMessage = new SendMessage();
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
        return outMessage.setText(inMessage.getText());
        //Отправляем сообщение
//        execute(outMessage);
    }
}
