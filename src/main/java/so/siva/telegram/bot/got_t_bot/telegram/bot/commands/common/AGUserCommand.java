package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common;

import org.springframework.beans.factory.annotation.Autowired;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

public abstract class AGUserCommand extends ACommand {

    @Autowired
    protected IUserService userService;

    public AGUserCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    public AGUserCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController, boolean isAdminCommand) {
        super(commandIdentifier, description, gotBotListenerController, isAdminCommand);
    }

    protected GUser getCurrentUser(Long chatId){
        return userService.getUserByChatId(chatId);
    }

    protected GUser getCurrentUser(String chatId){
        return userService.getUserByChatId(Long.valueOf(chatId));
    }

    protected GUser updateCurrentUser(GUser gUser){
        return userService.updateUser(gUser);
    }
}
