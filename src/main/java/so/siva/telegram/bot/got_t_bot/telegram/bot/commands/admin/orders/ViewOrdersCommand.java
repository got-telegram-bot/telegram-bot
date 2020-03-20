package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.orders;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.AAdminCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralResponseBuilder.*;

@Component
public class ViewOrdersCommand extends AAdminCommand {


    public ViewOrdersCommand(GotBotListenerController gotBotListenerController) {
        super("view_orders", "посмотреть последние приказы игроков (house_domain[])", gotBotListenerController);
    }

    @Override
    public void execute(GUser currentAdmin, Chat chat, String[] arguments) {
        List<GUser> players = userService.getAllPlayers();

        if (arguments.length > 0){
            players = players.stream()
                    .filter(player -> Arrays.stream(arguments).anyMatch(argument -> argument.equals(player.getHouse().getDomain())))
                    .collect(Collectors.toList());
        }

        StringBuffer orders = new StringBuffer();
        orders.append("<b>").append("Приказы: \n").append("</b>");
        players.forEach(player -> {
            orders.append("<pre>");
            orders.append(player.getHouse().getRusName()).append(": \n");
            orders.append("</pre>");
            orders.append("<i>");
            orders.append(player.getLastOrderMessage());
            orders.append("</i>\n");
            orders.append("<code>-------------</code>");
            orders.append("\n");
        });
        execute(prepareAutoClosableMessage(orders.toString(), chat));
    }
}
