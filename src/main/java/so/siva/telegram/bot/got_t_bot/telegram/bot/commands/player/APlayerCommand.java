package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player;

import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.IRoleAccessable;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.AGUserCommand;
import so.siva.telegram.bot.got_t_bot.web.exceptions.IllegalGUserException;

public abstract class APlayerCommand extends AGUserCommand implements IRoleAccessable {

    public APlayerCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController, false);
    }


    @Override
    protected GUser getCurrentUser(Long chatId){
        return super.getCurrentUser(chatId);
    }

    @Override
    public void validateAccess(Long chatId){
        GUser currentUser = super.getCurrentUser(chatId);
        if (currentUser == null || currentUser.getHouse() == null){
            execute(prepareSendMessage("Вы должны быть участником игры для пользования этой командой", chatId));
            throw new IllegalGUserException();
        }
    }




}
