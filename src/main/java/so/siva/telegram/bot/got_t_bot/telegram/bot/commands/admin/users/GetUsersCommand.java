package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.essences.users.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.AAdminCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.parsers.HtmlTags;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareAutoClosableMessage;


@Component
public class GetUsersCommand extends AAdminCommand {

    @Autowired
    private IUserService userService;

    public GetUsersCommand(GotBotListenerController gotBotListenerController) {
        super("/get_users", "получить пользователей (порядковый_номер)", gotBotListenerController);
    }

    @Override
    public void execute(GUser currentAdmin, Chat chat, String[] arguments) {
        if (arguments.length == 0){
            execute(prepareAutoClosableMessage("Передайте порядковый номер", chat));
        }else {
            execute(prepareAutoClosableMessage( HtmlTags.PRE.getOpenTag() + userService.getAllUsersPageableByOne(Integer.valueOf(arguments[0])).toString() + HtmlTags.PRE.getCloseTag(), chat));
        }
    }
}
