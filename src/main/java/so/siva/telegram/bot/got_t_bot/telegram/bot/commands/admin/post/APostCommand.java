package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post;

import org.springframework.beans.factory.annotation.Autowired;
import so.siva.telegram.bot.got_t_bot.essences.admin.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.essences.admin.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.essences.admin.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.AAdminCommand;

import java.util.List;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.*;

public abstract class APostCommand extends AAdminCommand {

    @Autowired
    protected IAdminPostMessageService adminPostMessageService;

    public APostCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    protected void sendPostMessages(List<AdminPostMessage> messageList, Long chatIdToSend){

        messageList.forEach(m -> {
            if (AdminPostMessageType.TEXT.equals(m.getAdminPostMessageType())){
                execute(prepareSendMessage(m.getContent(), chatIdToSend));
            }
            if (AdminPostMessageType.PHOTO.equals(m.getAdminPostMessageType())){
                execute(prepareSendPhoto(m.getFileId(), m.getContent(), chatIdToSend));
            }
        });

    }
}
