package so.siva.telegram.bot.got_t_bot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.post.SendPostCommand;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.web.exceptions.DefaultException.DEFAULT_COMMAND_ERROR_MESSAGE;

@Component
public class AlarmBean {

    private Logger logger = LoggerFactory.getLogger(AlarmBean.class);

    private final AbsSender absSender;

    private final IUserService userService;

    private final String awakeMsg;

    public AlarmBean(AbsSender absSender, IUserService userService, @Value("${alarm.message}") String awakeMsg) {
        this.absSender = absSender;
        this.userService = userService;
        this.awakeMsg = awakeMsg;
        alarm(awakeMsg);
    }


    @PreDestroy
    public void onDestroy(){
        alarm("Выключаюсь");
        logger.info("--------------TEST_DESTROY_BEAN-----------");
    }

    private void alarm(String msg){

        List<GUser> usersToSendPost = userService.getAllUsers();
        usersToSendPost = usersToSendPost.stream().filter(igUser -> igUser.getChatId() != null).collect(Collectors.toList());

        try {
            usersToSendPost.forEach(igUser -> {
                try {
                    absSender.execute(prepareAlarmMsg(msg, igUser.getChatId()));
                } catch (TelegramApiException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        }catch (Throwable throwable){
            logger.error(throwable.getMessage());
        }
    }

    private SendMessage prepareAlarmMsg(String msg, Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(msg);
        sendMessage.setChatId(chatId);
        return sendMessage;
    }
}
