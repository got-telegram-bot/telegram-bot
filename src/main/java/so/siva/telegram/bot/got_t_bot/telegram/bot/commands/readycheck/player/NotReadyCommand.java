package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.readycheck.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

@Component
public class NotReadyCommand extends ACommand {

    @Autowired
    private IUserService userService;

    public NotReadyCommand(GotBotListenerController gotBotListenerController) {
        super("not_ready", "отменить проверку готовности", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User tUser, Chat chat, String[] strings) {
        GUser gUser = userService.getUserByChatId(chat.getId());
        gUser.setReady(false);
        userService.updateUser(gUser);

        execute(absSender, prepareSendMessage("Готовность отменена", chat.getId().toString()), tUser);
    }
}
