package so.siva.telegram.bot.got_t_bot.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class AdminPostMessageCollector {

    private static final Logger logger = LoggerFactory.getLogger(AdminPostMessageCollector.class);

    @Autowired
    private IAdminPostMessageService adminPostMessageService;


    @Around(value = "execution(* so.siva.telegram.bot.got_t_bot.web.aop.LooperHack.apply(..)) && args(update))")
    public Object captureAdminPostMessagesListening(ProceedingJoinPoint joinPoint, Update update) throws Throwable {
        Message telegramMessage = update.getMessage();
        List<IAdminPostMessage> adminPostMessages;
        try {
            adminPostMessages = new ArrayList<>(adminPostMessageService.getMessages(telegramMessage.getChatId().toString()));
        }catch (Throwable throwable){
            logger.error("Error get admin Messages");
            return joinPoint.proceed(new Object[]{update});
        }


        if (adminPostMessageService.getMessages(telegramMessage.getChatId().toString()).size() > 0){

            if (telegramMessage.hasText()){
                IAdminPostMessage newAdminPostMessage = new AdminPostMessage();
                newAdminPostMessage.setNumberInPost(adminPostMessages.get(adminPostMessages.size() - 1).getNumberInPost() + 1);
                newAdminPostMessage.setAdminLogin(adminPostMessageService.getLoginByChatId(telegramMessage.getChatId().toString()));
                newAdminPostMessage.setAdminPostMessageType(AdminPostMessageType.TEXT);
                newAdminPostMessage.setContent(telegramMessage.getText());
                adminPostMessageService.addMessage(newAdminPostMessage);
                return joinPoint.proceed(new Object[]{new Update()});
            }
            if (telegramMessage.hasPhoto()){
                PhotoSize photoSize = telegramMessage.getPhoto().stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
                if (photoSize != null){
                    IAdminPostMessage newAdminPostMessage = new AdminPostMessage();
                    newAdminPostMessage.setNumberInPost(adminPostMessages.get(adminPostMessages.size() - 1).getNumberInPost() + 1);
                    newAdminPostMessage.setAdminLogin(adminPostMessageService.getLoginByChatId(telegramMessage.getChatId().toString()));
                    newAdminPostMessage.setAdminPostMessageType(AdminPostMessageType.PHOTO);
                    newAdminPostMessage.setContent(telegramMessage.getCaption());
                    newAdminPostMessage.setFileId(photoSize.getFileId());
                    adminPostMessageService.addMessage(newAdminPostMessage);
                    return joinPoint.proceed(new Object[]{new Update()});
                }
                else {
                    logger.error("PhotoSize is null");
                }

            }

        }
        return joinPoint.proceed(new Object[]{update});
    }
}
