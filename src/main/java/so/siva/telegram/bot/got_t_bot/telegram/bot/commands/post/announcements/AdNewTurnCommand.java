package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post.announcements;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;


@Component
public class AdNewTurnCommand extends AAdCommand {


    public AdNewTurnCommand(GotBotListenerController gotBotListenerController) {
        super("/ad_new_turn", "объявить новый ход (номер_хода)", gotBotListenerController);
    }

    @Override
    protected String prepareTemplate(String[] strings){
        return String.format("-- Ход № %s --", strings[0]);
    }

    @Override
    protected void checkParam(String[] param){
        if (param.length != 1){
            throw new IllegalArgumentException("Неверное количество параметров");
        }
        if (StringUtils.isEmpty(param[0])){
            throw new IllegalArgumentException("Не передан номер хода");
        }
    }

}
