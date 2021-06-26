package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.essences.users.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.ISpecifiedMessageHandler;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareAutoClosableMessage;

@Component
public class PlayerMessageHandler implements ISpecifiedMessageHandler {

    private Logger logger = LoggerFactory.getLogger(PlayerMessageHandler.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private AbsSender absSender;

    @Override
    public void processMessage(Message message, GUser currentPlayer) throws TelegramApiException {

        if (message.hasText()) {

            currentPlayer.setLastOrderMessage(message.getText());

            absSender.execute(prepareAutoClosableMessage("Сохранено:\n "
                    + "<i>"
                    + userService.updateUser(currentPlayer).getLastOrderMessage()
                    + "</i>"
                    , message.getChat()));
        }
    }
}
