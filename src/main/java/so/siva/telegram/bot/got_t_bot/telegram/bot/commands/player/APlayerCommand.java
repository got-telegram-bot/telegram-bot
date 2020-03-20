package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.IRoleAccessible;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.AGUserCommand;
import so.siva.telegram.bot.got_t_bot.web.exceptions.IllegalGUserException;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralResponseBuilder.*;

public abstract class APlayerCommand extends AGUserCommand implements IRoleAccessible {

    public APlayerCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        GUser currentUser = validateAccess(chat.getId());
        execute(currentUser, chat, arguments);
    }

    public abstract void execute(GUser currentPlayer, Chat chat, String[] arguments);

    @Override
    public GUser validateAccess(Long chatId){
        GUser currentUser = super.getCurrentUser(chatId);
        if (currentUser == null || currentUser.getHouse() == null){
            execute(prepareAutoClosableMessage("Вы должны быть участником игры для пользования этой командой", chatId));
            throw new IllegalGUserException();
        }
        return currentUser;
    }




}
