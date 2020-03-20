package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.function.BiConsumer;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralResponseBuilder.prepareAutoClosableMessage;

@Component
public class DefaultCommandRegistryConsumer implements BiConsumer<AbsSender, Message> {

    private Logger logger = LoggerFactory.getLogger(GotBotListenerController.class);
    @Override
    public void accept(AbsSender absSender, Message message) {

        try {
            absSender.execute(prepareAutoClosableMessage("Неизвестная команда", message.getChat()).setReplyToMessageId(message.getMessageId()));
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
    }
}
