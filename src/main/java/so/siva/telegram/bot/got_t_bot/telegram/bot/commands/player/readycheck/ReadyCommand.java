package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.readycheck;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.APlayerCommand;

@Component
public class ReadyCommand extends APlayerCommand {
    public ReadyCommand(GotBotListenerController gotBotListenerController) {
        super("ready", "подтвердить готовность", gotBotListenerController);
    }

    @Override
    public void execute(Chat chat, String[] strings) {
        GUser gUser = getCurrentUser(chat.getId());
        gUser.setReady(true);
        updateCurrentUser(gUser);

        execute(prepareSendMessage("Готовность принята", chat.getId().toString()));
    }
}
