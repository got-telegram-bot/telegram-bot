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
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class StartPostCommand extends APostCommand {

    public StartPostCommand(GotBotListenerController gotBotListenerController) {
        super("/start_post", "начать пост", gotBotListenerController);
    }


    private Logger logger = LoggerFactory.getLogger(StartPostCommand.class);

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        String chatId = chat.getId().toString();
        message.setChatId(chatId);
        if (adminPostMessageService.getMessages(chatId).size() > 0){
            message.setText("Запись сообщений уже начата");
            execute(absSender, message, telegramUser);
            return;
        }
        try {
            adminPostMessageService.startPost(chatId);
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }
        if (adminPostMessageService.getMessages(chatId).size() > 0){
            message.setText("Начата запись сообщений");
            execute(absSender, message, telegramUser);
            return;
        }

        message.setText(DEFAULT_COMMAND_ERROR_MESSAGE);
        execute(absSender, message, telegramUser);
    }
}
