package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.function.BiConsumer;

@Component
public class DefaultCommandRegistryConsumer implements BiConsumer<AbsSender, Message> {

    private Logger logger = LoggerFactory.getLogger(GotBotListenerController.class);
    @Override
    public void accept(AbsSender absSender, Message message) {

        try {
            SendMessage errorMessage = new SendMessage();
            errorMessage.setText("Неизвестная команда");
            errorMessage.setChatId(message.getChatId());
            errorMessage.setReplyToMessageId(message.getMessageId());
            absSender.execute(errorMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }
}
