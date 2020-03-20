package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.AMarkUppedCommand;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.InlineMarkupBuilder.DEFAULT_EXIT_BUTTON_LABEL;

public abstract class AGUserCommand extends AMarkUppedCommand {

    @Autowired
    protected IUserService userService;

    public AGUserCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    @Override
    public void execute(Chat chat, String[] arguments) {
        GUser currentUser = getCurrentUser(chat);
        execute(currentUser, chat, arguments);
    }

    public abstract void execute(GUser currentUser, Chat chat, String[] arguments);

    protected GUser getCurrentUser(Long chatId){
        return userService.getUserByChatId(chatId);
    }

    protected GUser getCurrentUser(Chat chat){
        return getCurrentUser(chat.getId());
    }

    protected GUser getCurrentUser(String chatId){
        return userService.getUserByChatId(Long.valueOf(chatId));
    }

    protected GUser updateCurrentUser(GUser gUser){
        return userService.updateUser(gUser);
    }
}
