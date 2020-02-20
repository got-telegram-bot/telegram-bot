package so.siva.telegram.bot.got_t_bot.web.aop;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.function.Function;


/**
 * Чтобы обернуть обработку сообщения в АОП (костыль, т.к. метод обработки апдейтов в GotBotListenerController не вызывается из бина спринга)
 */
@Component
public class LooperHack implements Function<Update, Update> {

    @Override
    public Update apply(Update update) {
        return update;
    }
}
