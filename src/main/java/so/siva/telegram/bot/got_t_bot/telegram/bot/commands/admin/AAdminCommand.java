package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.IRoleAccessible;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.AGUserCommand;
import so.siva.telegram.bot.got_t_bot.web.exceptions.IllegalGUserException;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.producers.GeneralResponseProducer.*;

public abstract class AAdminCommand extends AGUserCommand implements IRoleAccessible {

    public AAdminCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        GUser currentUser = validateAccess(chat.getId());
        execute(currentUser, chat, arguments);
    }

    public abstract void execute(GUser currentAdmin, Chat chat, String[] arguments);

    @Override
    public GUser validateAccess(Long chatId){
        GUser currentUser = super.getCurrentUser(chatId);
        if (currentUser == null || !currentUser.isAdmin()){
            execute(prepareAutoClosableMessage("У вас нет прав администратора для пользования этой командой", chatId));
            throw new IllegalGUserException();
        }
        return currentUser;
    }
}
