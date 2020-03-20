package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.readycheck;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.*;

@Component
public class CancelReadyCheckCommand extends AReadyCheckCommand {

    public CancelReadyCheckCommand(GotBotListenerController gotBotListenerController) {
        super("cancel_ready_check", "окончить проверку готовности", gotBotListenerController);
    }

    @Override
    protected boolean switchAdminReadyCheckState() {
        return false;
    }

    @Override
    protected SendMessage prepareAdMessage(Long chatId) {
        return prepareSendMessage(
                "-- Проверка готовности окончена-- ",
                chatId);
    }
}
