package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.readycheck;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.AAdminCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.SendPostCommand;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AReadyCheckCommand extends AAdminCommand {

    private Logger logger = LoggerFactory.getLogger(SendPostCommand.class);


    public AReadyCheckCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    @Override
    public void execute(AbsSender absSender, User tUser, Chat chat, String[] strings) {
        List<GUser> gUsers = userService.getUsersForReadyCheck();
        if (gUsers.stream().anyMatch(gUser -> gUser.getChatId() == null)){
            String errorMsg = "Пользователь не авторизован (chatId = null)";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        GUser currentAdmin = gUsers.stream()
                .filter(gUser -> gUser.isAdmin() && chat.getId().equals(gUser.getChatId()))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Администратор не найден"));

        currentAdmin.setReady(switchAdminReadyCheckState());

        List<GUser> players = gUsers.stream().filter(gUser -> !gUser.isAdmin() && gUser.getHouse() != null).collect(Collectors.toList());
        players.forEach(player -> {
            player.setReady(false);
            userService.updateUser(player);
        });

        userService.updateUser(currentAdmin);

        gUsers.forEach(gUser -> execute(prepareAdMessage(gUser.getChatId())));

    }

    protected abstract boolean switchAdminReadyCheckState();
    protected abstract SendMessage prepareAdMessage(Long chatId);
}
