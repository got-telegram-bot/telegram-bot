package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.List;

public abstract class APostCommand extends ACommand {

    @Autowired
    protected IAdminPostMessageService adminPostMessageService;

    public APostCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    protected void sendPostMessages(AbsSender absSender, User telegramUser, List<AdminPostMessage> messageList, String chatIdToSend){

        messageList.forEach(m -> {
            if (AdminPostMessageType.TEXT.equals(m.getAdminPostMessageType())){
                execute(absSender, prepareSendMessage(m.getContent(), chatIdToSend), telegramUser);
            }
            if (AdminPostMessageType.PHOTO.equals(m.getAdminPostMessageType())){
                execute(absSender, prepareSendPhoto(m.getFileId(), m.getContent(), chatIdToSend), telegramUser);
            }
        });

    }
}
