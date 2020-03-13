package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.orders;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.APlayerCommand;

@Component
public class ViewLastOrderCommand extends APlayerCommand {

    public ViewLastOrderCommand(GotBotListenerController gotBotListenerController) {
        super("view_last_order", "посмотреть свой последний приказ", gotBotListenerController);
    }

    @Override
    public void execute(GUser currentPlayer, Chat chat, String[] arguments) {
        execute(prepareSendMessage(StringUtils.isEmpty(currentPlayer.getLastOrderMessage()) ? "<code>Нет отданных приказов</code>" : currentPlayer.getLastOrderMessage(), chat));
    }
}
