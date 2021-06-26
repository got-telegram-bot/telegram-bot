package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.readycheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.essences.users.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.parsers.HtmlTags;

import java.util.List;
import java.util.stream.Collectors;import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.*;

@Component
public class ViewReadyCheck extends ACommand {

    @Autowired
    private IUserService userService;

    public ViewReadyCheck(GotBotListenerController gotBotListenerController) {
        super("view_ready_check", "посмотреть готовность", gotBotListenerController);
    }

    @Override
    public void execute(Chat chat, String[] strings) {
        List<GUser> gUsers = userService.getUsersForReadyCheck();
        List<GUser> players = gUsers.stream().filter(gUser -> !gUser.isAdmin() && gUser.getHouse() != null).collect(Collectors.toList());

        if (gUsers.stream().noneMatch(gUser -> gUser.isAdmin() && gUser.isReady())){
            execute(prepareSendMessage("Проверка готовности не была инициирована", chat));
            return;
        }

        StringBuffer message = new StringBuffer();
        message.append(HtmlTags.BOLD.getOpenTag()).append("Готовы: \n").append(HtmlTags.BOLD.getCloseTag()).append(HtmlTags.PRE.getOpenTag());
        players.forEach(player -> {
            int fillCount = 15 - player.getHouse().getRusName().length();
            message.append(player.getHouse().getRusName());
            message.append(" ");
            for (int i = 0; i < fillCount; i++) {
                message.append("-");
            }
            message.append(" ");
            message.append(player.isReady() ? "✅" : "❌")
                   .append("\n");
        });

        execute(prepareAutoClosableMessage(message.append(HtmlTags.PRE.getCloseTag()).toString(), chat));
    }
}
