package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class SendPostCommand extends APostCommand {

    private final String CANCEL_FLAG = "cancel";

    public SendPostCommand(GotBotListenerController gotBotListenerController) {
        super("/send_post", "отправить пост всем авторизованным пользователям (cancel, house_domain[bar, lan, str,...])", gotBotListenerController);
    }

    @Autowired
    private CancelPostCommand cancelPostCommand;

    private Logger logger = LoggerFactory.getLogger(SendPostCommand.class);

    @Override
    public void execute(GUser currentAdmin, Chat chat, String[] strings) {
        List<GUser> usersToSendPost = userService.getAllUsers();

        List<String> housesToSend = Arrays.stream(strings).filter(s -> Arrays.stream(Houses.values()).anyMatch(houses -> houses.getDomain().equals(s))).collect(Collectors.toList());

        usersToSendPost = usersToSendPost.stream().filter(igUser -> igUser.getChatId() != null).collect(Collectors.toList());

        if (housesToSend.size() != 0){
            usersToSendPost = usersToSendPost.stream().filter(gUser -> (gUser.getHouse() != null) || gUser.isAdmin())
                    .filter(gUser -> (housesToSend.stream().anyMatch(house -> gUser.isAdmin() ||  (gUser.getHouse().getDomain() != null && gUser.getHouse().getDomain().equals(house))))).collect(Collectors.toList());
        }

        String posterChatId = chat.getId().toString();

        SendMessage errorMessage = new SendMessage();
        errorMessage.setChatId(posterChatId);
        String errorMessageText = DEFAULT_COMMAND_ERROR_MESSAGE;
        try {
            List<AdminPostMessage> messageList = new ArrayList<>(adminPostMessageService.getCombinedMessages(posterChatId));

            if (messageList.size() > 0){
                usersToSendPost.forEach(igUser -> sendPostMessages(messageList, igUser.getChatId().toString()));

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
