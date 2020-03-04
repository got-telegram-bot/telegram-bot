package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.List;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class ViewPostCommand extends APostCommand {

    public ViewPostCommand(GotBotListenerController gotBotListenerController) {
        super("/view_post", "просмотреть пост", gotBotListenerController);
    }

    private Logger logger = LoggerFactory.getLogger(ViewPostCommand.class);

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        String chatId = chat.getId().toString();

        SendMessage errorMessage = new SendMessage();
        errorMessage.setChatId(chatId);
        String errorMessageText = DEFAULT_COMMAND_ERROR_MESSAGE;
        try {
            List<IAdminPostMessage> messageList = new ArrayList<>(adminPostMessageService.getCombinedMessages(chatId));

            if (messageList.size() > 0){
                sendPostMessages(absSender, telegramUser, messageList, chatId);
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
