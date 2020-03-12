package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.actions;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

@Component
public class HouseRandomCommand extends ACommand {

    public HouseRandomCommand(GotBotListenerController gotBotListenerController) {
        super("/house_random", "выдать случайные дома для игроков", gotBotListenerController);
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());

        message.setText("Команда пока не работает");
        execute(message);
    }
}
