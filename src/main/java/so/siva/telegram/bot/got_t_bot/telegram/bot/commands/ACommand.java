package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class ACommand extends BotCommand {

    public ACommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }


    public void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);

        } catch (TelegramApiException e) {
            System.out.println("[ERROR] - command error: " + user.getId() + getCommandIdentifier() +  e.getMessage());
        }
    }

}
