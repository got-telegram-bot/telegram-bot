package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class CancelPostCommand extends APostCommand {

    public CancelPostCommand(GotBotListenerController gotBotListenerController) {
        super("/cancel_post", "отменить пост", gotBotListenerController);
    }

    private Logger logger = LoggerFactory.getLogger(CancelPostCommand.class);

    @Override
    public void execute(GUser currentAdmin, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        String chatId = chat.getId().toString();

        message.setChatId(chatId);
        if (adminPostMessageService.getMessages(chatId).size() == 0){
            message.setText("Запись сообщений не была начата");
            execute(message);
            return;
        }
        try {
            adminPostMessageService.cancelPost(chatId);
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }
        if (adminPostMessageService.getMessages(chatId).size() == 0){
            message.setText("Запись сообщений отменена");
            execute(message);
            return;
        }

        message.setText(DEFAULT_COMMAND_ERROR_MESSAGE);
        execute(message);
    }
}
