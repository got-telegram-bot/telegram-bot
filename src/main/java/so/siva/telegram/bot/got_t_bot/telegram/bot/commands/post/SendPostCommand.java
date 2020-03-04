package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class SendPostCommand extends APostCommand {

    public SendPostCommand(GotBotListenerController gotBotListenerController) {
        super("/send_post", "отправить пост всем авторизованным пользователям", gotBotListenerController);
    }

    @Autowired
    private CancelPostCommand cancelPostCommand;

    @Autowired
    private IUserService userService;

    private Logger logger = LoggerFactory.getLogger(SendPostCommand.class);

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        List<IGUser> usersToSendPost = userService.getAllUsers();

        usersToSendPost = usersToSendPost.stream().filter(igUser -> igUser.getChatId() != null).collect(Collectors.toList());

        String posterChatId = chat.getId().toString();

        SendMessage errorMessage = new SendMessage();
        errorMessage.setChatId(posterChatId);
        String errorMessageText = DEFAULT_COMMAND_ERROR_MESSAGE;
        try {
            List<IAdminPostMessage> messageList = new ArrayList<>(adminPostMessageService.getCombinedMessages(posterChatId));

            if (messageList.size() > 0){
                usersToSendPost.forEach(igUser -> sendPostMessages(absSender, telegramUser, messageList, igUser.getChatId().toString()));

                cancelPostCommand.execute(absSender, telegramUser, chat, strings);
                return;
            }else {
                errorMessage.setText("Нет доступных сообщений");
                return;
            }
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }

        errorMessage.setText(errorMessageText);
        execute(absSender, errorMessage, telegramUser);
    }
}
