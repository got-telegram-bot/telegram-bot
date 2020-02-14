package so.siva.telegram.bot.got_t_bot.telegram.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class GotBotHandler extends TelegramLongPollingBot {

//    @Value("${telegram.bot.token}")
    private final String botToken = "1085351467:AAET2sqvY35U96D-nImy9ZVYPP7bkD-Nczc";

//    @Value("${telegram.bot.username}")
    private final String botUserName = "got_board_bot";

    private final Long chatIdDrenal = 427924506L;
    private final Long chatIdRainbow = 416724770L;
    private final Long chatIdSiva = 381855899L;

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try{
            if (update.hasMessage() && update.getMessage().hasText()) {
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
                outMessage.setText(inMessage.getText());
                //Отправляем сообщение
                execute(outMessage);
            }
            if (update.hasMessage() && update.getMessage().hasSticker()) {
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
                outMessage.setSticker(inMessage.getSticker().getFileId());
                //Отправляем сообщение
                execute(outMessage);
            }
        }catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    public String getBotUsername() {
        return this.botUserName;
    }

}
