package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.ISpecifiedMessageHandler;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareAutoClosableMessage;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareSendPhoto;

@Component
public class AdminMessageHandler implements ISpecifiedMessageHandler {
    private Logger logger = LoggerFactory.getLogger(AdminMessageHandler.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private AbsSender absSender;

    @Autowired
    private AdminPostMessageCollector postMessageCollector;

    @Override
    public void processMessage(Message message, GUser currentAdmin) throws TelegramApiException {
        if(postMessageCollector.captureAdminPostMessages(message) == null){
            logger.info("admin message captured");
            return;
        }

        if (message.hasText()) {
            currentAdmin.setLastOrderMessage(message.getText());
            absSender.execute(prepareAutoClosableMessage("Сохранено:\n "
                            + "<i>"
                            + userService.updateUser(currentAdmin).getLastOrderMessage()
                            + "</i>"
                    , message.getChat()));
        }

        if (message.hasPhoto()) {
            absSender.execute(
                    prepareSendPhoto(
                            message.getPhoto().get(0).getFileId(),
                            message.getCaption() + "\n" + message.getPhoto().get(0).getFileId(),
                            message.getChat())
            );
        }
    }
}
