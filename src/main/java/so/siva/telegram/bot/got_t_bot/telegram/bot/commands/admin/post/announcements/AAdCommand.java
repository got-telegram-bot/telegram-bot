package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.announcements;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.AdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.APostCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.StartPostCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.ViewPostCommand;

/**
 * Команды для общих оповещений (ad - анонс)
 */
public abstract class AAdCommand extends APostCommand {

    @Autowired
    protected AdminPostMessageService adminPostMessageService;

    @Autowired
    protected StartPostCommand startPostCommand;

    @Autowired
    protected ViewPostCommand viewPostCommand;

    public AAdCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }


    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        try {
            checkParam(strings);
        }catch (Throwable throwable){
            SendMessage message = new SendMessage();
            message.setText(throwable.getMessage());
            message.setChatId(chat.getId());
            execute(message);
            return;
        }

        startPostCommand.execute(absSender, telegramUser, chat, null);
        AdminPostMessage adminPostMessage = new AdminPostMessage();
        adminPostMessage.setNumberInPost(1);
        adminPostMessage.setAdminLogin(adminPostMessageService.getLoginByChatId(chat.getId().toString()));
        adminPostMessage.setContent(prepareTemplate(strings));
        adminPostMessage.setAdminPostMessageType(AdminPostMessageType.TEXT);

        adminPostMessageService.addMessage(adminPostMessage);

        viewPostCommand.execute(absSender, telegramUser, chat, null);

    }

    protected abstract String prepareTemplate(String[] strings);

    protected abstract void checkParam(String[] param);
}
