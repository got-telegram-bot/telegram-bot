package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.parsers.IncomingMessageHtmlParser.parseInHtml;

@Component
public class AdminPostMessageCollector {

    private static final Logger logger = LoggerFactory.getLogger(AdminPostMessageCollector.class);

    @Autowired
    private IAdminPostMessageService adminPostMessageService;

    public Message captureAdminPostMessages(Message telegramMessage){
        List<AdminPostMessage> adminPostMessages;
        try {
            adminPostMessages = new ArrayList<>(adminPostMessageService.getMessages(telegramMessage.getChatId().toString()));
        }catch (Throwable throwable){
            logger.error("Error get admin Messages");
            return telegramMessage;
        }

        if (adminPostMessageService.getMessages(telegramMessage.getChatId().toString()).size() > 0){

            if (telegramMessage.hasText()){
                AdminPostMessage newAdminPostMessage = new AdminPostMessage();
                newAdminPostMessage.setNumberInPost(adminPostMessages.get(adminPostMessages.size() - 1).getNumberInPost() + 1);
                newAdminPostMessage.setAdminLogin(adminPostMessageService.getLoginByChatId(telegramMessage.getChatId().toString()));
                newAdminPostMessage.setAdminPostMessageType(AdminPostMessageType.TEXT);
                newAdminPostMessage.setContent(parseInHtml(telegramMessage));
                adminPostMessageService.addMessage(newAdminPostMessage);
                return null;
            }
            if (telegramMessage.hasPhoto()){
                PhotoSize photoSize = telegramMessage.getPhoto().stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
                if (photoSize != null){
                    AdminPostMessage newAdminPostMessage = new AdminPostMessage();
                    newAdminPostMessage.setNumberInPost(adminPostMessages.get(adminPostMessages.size() - 1).getNumberInPost() + 1);
                    newAdminPostMessage.setAdminLogin(adminPostMessageService.getLoginByChatId(telegramMessage.getChatId().toString()));
                    newAdminPostMessage.setAdminPostMessageType(AdminPostMessageType.PHOTO);
                    newAdminPostMessage.setContent(telegramMessage.getCaption());
                    newAdminPostMessage.setFileId(photoSize.getFileId());
                    adminPostMessageService.addMessage(newAdminPostMessage);
                    return null;
                }
                else {
                    logger.error("PhotoSize is null");
                    throw new NullPointerException("PhotoSize is null");
                }
            }
        }
        return telegramMessage;
    }
}
