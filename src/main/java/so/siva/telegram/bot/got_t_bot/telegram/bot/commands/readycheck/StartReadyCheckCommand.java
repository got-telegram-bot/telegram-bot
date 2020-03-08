package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.readycheck;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

@Component
public class StartReadyCheckCommand extends AReadyCheckCommand {

    public StartReadyCheckCommand(GotBotListenerController gotBotListenerController) {
        super("start_ready_check", "начать проверку готовности", gotBotListenerController);
    }

    @Override
    protected boolean switchAdminReadyCheckState() {
        return true;
    }

    @Override
    protected SendMessage prepareAdMessage(Long chatId) {
        return prepareSendMessage(
                "-- Начата проверка готовности -- " +
                        "\n Отправьте /ready чтобы подтвердить готовность. " +
                        "\n Чтобы отменить, отправьте /not_ready.",
                String.valueOf(chatId));
    }
}
