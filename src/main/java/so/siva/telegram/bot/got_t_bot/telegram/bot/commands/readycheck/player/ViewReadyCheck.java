package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.readycheck.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ViewReadyCheck extends ACommand {

    @Autowired
    private IUserService userService;

    public ViewReadyCheck(GotBotListenerController gotBotListenerController) {
        super("view_ready_check", "посмотреть готовность", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User tUser, Chat chat, String[] strings) {
        List<GUser> gUsers = userService.getUsersForReadyCheck();
        List<GUser> players = gUsers.stream().filter(gUser -> !gUser.isAdmin() && gUser.getHouse() != null).collect(Collectors.toList());

        if (gUsers.stream().noneMatch(gUser -> gUser.isAdmin() && gUser.isReady())){
            execute(absSender, prepareSendMessage("Проверка готовности не была инициирована", String.valueOf(chat.getId())), tUser);
            return;
        }

        StringBuffer message = new StringBuffer();
        message.append("Готовы: \n");
        players.forEach(player -> {
            int fillCount = 20 - player.getHouse().getRusName().length();
            message.append("<b>").append(player.getHouse().getRusName()).append("</b>");
            message.append(" ");
            for (int i = 0; i < fillCount; i++) {
                message.append("-");
            }
            message.append(" ");
            message.append(player.isReady() ? "✅" : "❌")
                   .append("\n");
        });

        execute(absSender, prepareSendMessage(message.toString(), String.valueOf(chat.getId())), tUser);
    }
}
