package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareSendMessage;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareSendPhoto;
import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class PublishPostCommand extends APostCommand {

    private final String CANCEL_FLAG = "cancel";

    public PublishPostCommand(GotBotListenerController gotBotListenerController) {
        super("/pub_post", "опубликовать пост в канал", gotBotListenerController);
    }

    @Autowired
    private CancelPostCommand cancelPostCommand;

    @Value("${telegram.game_channel.id}")
    private Long channelChatId;

    private Logger logger = LoggerFactory.getLogger(PublishPostCommand.class);

    @Override
    public void execute(GUser currentAdmin, Chat chat, String[] strings) {
        String posterChatId = chat.getId().toString();

        SendMessage errorMessage = new SendMessage();
        errorMessage.setChatId(posterChatId);
        String errorMessageText = DEFAULT_COMMAND_ERROR_MESSAGE;
        try {
            List<AdminPostMessage> messageList = new ArrayList<>(adminPostMessageService.getCombinedMessages(posterChatId));

            if (messageList.size() > 0){
                messageList.forEach(m -> {
                    if (AdminPostMessageType.TEXT.equals(m.getAdminPostMessageType())){
                        execute(prepareSendMessage(m.getContent(), channelChatId));
                    }
                    if (AdminPostMessageType.PHOTO.equals(m.getAdminPostMessageType())){
                        execute(prepareSendPhoto(m.getFileId(), m.getContent(), channelChatId));
                    }
                });

                if (Arrays.asList(strings).contains(CANCEL_FLAG)){
                    cancelPostCommand.execute(currentAdmin, chat, strings);
                }
            }else {
                errorMessage.setText("Нет доступных сообщений");
            }
            return;
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }

        errorMessage.setText(errorMessageText);
        execute(errorMessage);
    }
}
