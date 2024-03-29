package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.announcements;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;


//@Component
public class AdExMarchCommand extends AAdCommand {

    private final static String EXE_MARCH_MAIN_TEMPLATE = "• \uD83D\uDEA9 Поход <b>%s</b> - «%s»: \uD83D\uDEA9";


    public AdExMarchCommand(GotBotListenerController gotBotListenerController) {
        super("/ad_exe_march", "объявить поход (дом откуда )", gotBotListenerController);
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
