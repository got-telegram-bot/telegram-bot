package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.post.announcements;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.Arrays;


@Component
public class AdNewCombatCommand extends AAdCommand {


    public AdNewCombatCommand(GotBotListenerController gotBotListenerController) {
        super("/ad_new_combat", "объявить новый бой (название_локации нападающий обороняющийся)", gotBotListenerController);
    }

    @Override
    protected String prepareTemplate(String[] params){
        for (int i = 1; i < 3; i++) {
            int finalI = i;
            Arrays.stream(Houses.values()).forEach(house -> {
                if (house.getDomain().equals(params[finalI])) {
                    params[finalI] = house.getRusName();
                }
            });
        }

        return String.format("-- ⚔ Бой. «%s». %s vs %s ⚔ --", (Object[]) params);
    }

    @Override
    protected void checkParam(String[] params){
        if (params.length != 3){
            throw new IllegalArgumentException("Неверное количество параметров");
        }

        for (String param : params){
            if (StringUtils.isEmpty(param)){
                throw new IllegalArgumentException("Не передан парметр");
            }
        }

    }

}
