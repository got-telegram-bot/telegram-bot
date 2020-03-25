package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.orders;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.AAdminCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.parsers.HtmlTags;

import javax.swing.text.html.HTML;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.*;

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
        orders.append(HtmlTags.BOLD.getOpenTag()).append("Приказы: \n").append(HtmlTags.BOLD.getCloseTag());
        players.forEach(player -> {
            orders.append(HtmlTags.PRE.getOpenTag());
            orders.append(player.getHouse().getRusName()).append(": \n");
            orders.append(HtmlTags.PRE.getCloseTag());
            orders.append(HtmlTags.ITALIC.getOpenTag());
            orders.append(player.getLastOrderMessage());
            orders.append(HtmlTags.ITALIC.getCloseTag()).append("\n");
            orders.append(HtmlTags.CODE.getOpenTag()).append("-------------").append(HtmlTags.CODE.getCloseTag());
            orders.append("\n");
        });
        execute(prepareAutoClosableMessage(orders.toString(), chat));
    }
}
