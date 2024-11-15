package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.essences.admin.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.List;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.DEFAULT_AUTO_CLOSABLE_LABEL;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.InlineMarkupBuilder.createAutoClosableMarkup;
import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class ViewPostCommand extends APostCommand {

    public ViewPostCommand(GotBotListenerController gotBotListenerController) {
        super("/view_post", "просмотреть пост", gotBotListenerController);
    }

    private Logger logger = LoggerFactory.getLogger(ViewPostCommand.class);

    @Override
    public void execute(GUser currentAdmin, Chat chat, String[] strings) {
        Long chatId = chat.getId();

        SendMessage errorMessage = new SendMessage().setReplyMarkup(createAutoClosableMarkup(DEFAULT_AUTO_CLOSABLE_LABEL));
        errorMessage.setChatId(chatId);
        String errorMessageText = DEFAULT_COMMAND_ERROR_MESSAGE;
        try {
            List<AdminPostMessage> messageList = new ArrayList<>(adminPostMessageService.getCombinedMessages(chatId.toString()));

            if (messageList.size() > 0){
                sendPostMessages(messageList, chatId);
                return;
            }else {
                errorMessage.setText("Нет доступных сообщений");
                return;
            }
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }

        errorMessage.setText(errorMessageText);
        execute(errorMessage);
    }
}
