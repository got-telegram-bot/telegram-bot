package so.siva.telegram.bot.got_t_bot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.essences.users.IUserService;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareAutoClosableMessage;

@Component
public class AlarmBean {

    private Logger logger = LoggerFactory.getLogger(AlarmBean.class);

    private final AbsSender absSender;

    private final IUserService userService;

    private final String shutdownMsg;

    public AlarmBean(AbsSender absSender, IUserService userService,
                     @Value("${alarm.message.awake}") String awakeMsg, @Value("${alarm.message.shutdown}") String shutdownMsg) {
        this.absSender = absSender;
        this.userService = userService;
        this.shutdownMsg = shutdownMsg;
        if (!StringUtils.isEmpty(awakeMsg)){
            alarm(awakeMsg);
        }
    }


    @PreDestroy
    public void onDestroy(){
        if (!StringUtils.isEmpty(this.shutdownMsg)){
            alarm(this.shutdownMsg);
        }
    }

    private void alarm(String msg){

        List<GUser> usersToSendPost = userService.getAllUsers();
        usersToSendPost = usersToSendPost.stream().filter(igUser -> igUser.getChatId() != null && igUser.isAdmin()).collect(Collectors.toList());

        try {
            usersToSendPost.forEach(igUser -> {
                try {
                    absSender.execute(prepareAutoClosableMessage(msg, igUser.getChatId()));
                } catch (TelegramApiException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }
    }
}
