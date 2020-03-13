package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.ISpecifiedMessageHandler;

@Component
public class DefaultMessageHandler implements ISpecifiedMessageHandler {
    private Logger logger = LoggerFactory.getLogger(DefaultMessageHandler.class);

    @Autowired
    private AbsSender absSender;

    /**
     * @param currentUser can be null
     */
    @Override
    public void processMessage(Message message, GUser currentUser) throws TelegramApiException {

        if (message.hasText()) {

            SendMessage answer = new SendMessage();
            answer.setChatId(message.getChatId());
            answer.setText(message.getText());


            absSender.execute(answer);
        }
        if (message.hasPhoto()) {

            SendPhoto answer = new SendPhoto();
            answer.setChatId(message.getChatId());

            answer.setPhoto(message.getPhoto().get(0).getFileId());
            absSender.execute(answer);
        }
    }
}