package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.List;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class ViewPostCommand extends ACommand {

    public ViewPostCommand() {
        super("/view_post", "Просмотреть пост");
    }

    @Autowired
    private IAdminPostMessageService adminPostMessageService;

    @Autowired
    private IUserService userService;

    private Logger logger = LoggerFactory.getLogger(ViewPostCommand.class);

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        String chatId = chat.getId().toString();
        message.setChatId(chatId);
        List<IAdminPostMessage> messageList = new ArrayList<>();
        try {
            messageList.addAll(adminPostMessageService.getMessages(chatId));
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }
        StringBuilder messageBuilder = new StringBuilder();
        if (messageList.size() > 0){
            messageList.forEach(m -> {
                if (AdminPostMessageType.TEXT.equals(m.getAdminPostMessageType())){
                    messageBuilder.append(" \n");
                    messageBuilder.append(m.getContent() == null ? "" : m.getContent());
                    messageBuilder.append(" \n");
                }
            });

            message.setText(messageBuilder.toString());
            execute(absSender, message, telegramUser);
            return;
        }

        message.setText(DEFAULT_COMMAND_ERROR_MESSAGE);
        execute(absSender, message, telegramUser);
    }
}
