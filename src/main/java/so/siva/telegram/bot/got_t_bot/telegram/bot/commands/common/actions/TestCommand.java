package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.actions;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareAutoClosableMessage;

@Component
public class TestCommand extends ACommand {


    public TestCommand(GotBotListenerController gotBotListenerController) {
        super("/test", "test", gotBotListenerController);
    }

    @Override
    public void execute(Chat chat, String[] strings) {

        execute(prepareAutoClosableMessage("Test", chat));
    }


}
