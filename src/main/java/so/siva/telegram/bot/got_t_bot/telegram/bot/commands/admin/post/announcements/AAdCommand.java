package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.announcements;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
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
    public void execute(GUser currentAdmin, Chat chat, String[] strings) {
        try {
            checkParam(strings);
        }catch (Throwable throwable){
            SendMessage message = new SendMessage();
            message.setText(throwable.getMessage());
            message.setChatId(chat.getId());
            execute(message);
            return;
        }

        startPostCommand.execute(currentAdmin, chat, null);
        AdminPostMessage adminPostMessage = new AdminPostMessage();
        adminPostMessage.setNumberInPost(1);
        adminPostMessage.setAdminLogin(currentAdmin.getLogin());
        adminPostMessage.setContent(prepareTemplate(strings));
        adminPostMessage.setAdminPostMessageType(AdminPostMessageType.TEXT);

        adminPostMessageService.addMessage(adminPostMessage);

        viewPostCommand.execute(currentAdmin, chat, null);

    }

    protected abstract String prepareTemplate(String[] strings);

    protected abstract void checkParam(String[] param);
}
