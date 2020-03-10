package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.AMarkUppedCommand;


public abstract class AInfoCommand extends AMarkUppedCommand {

    public AInfoCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    public AInfoCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController, boolean isAdminCommand) {
        super(commandIdentifier, description, gotBotListenerController, isAdminCommand);
    }

    @Override
    protected String setExitButtonLabel() {
        return "✖ Выход ✖";
    }
}
