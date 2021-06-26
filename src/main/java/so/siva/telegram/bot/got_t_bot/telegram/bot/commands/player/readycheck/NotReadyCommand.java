package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.readycheck;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.APlayerCommand;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.*;

@Component
public class NotReadyCommand extends APlayerCommand {

    public NotReadyCommand(GotBotListenerController gotBotListenerController) {
        super("not_ready", "отменить проверку готовности", gotBotListenerController);
    }

    @Override
    public void execute(GUser currentPlayer, Chat chat, String[] strings) {
        currentPlayer.setReady(false);
        updateCurrentUser(currentPlayer);

        execute(prepareAutoClosableMessage("Готовность отменена", chat));
    }
}
