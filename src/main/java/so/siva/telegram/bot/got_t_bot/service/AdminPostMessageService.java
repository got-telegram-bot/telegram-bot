package so.siva.telegram.bot.got_t_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.dao.api.IAdminPostMessageDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AdminPostMessageService implements IAdminPostMessageService {

    @Autowired
    private IAdminPostMessageDao adminPostMessageDao;

    @Autowired
    private IUserService userService;

    @Override
    public void startPost(String chatId){
        String adminLogin = getLoginByChatId(chatId);
        validateAdminLogin(adminLogin);
        IAdminPostMessage message = new AdminPostMessage();
        message.setAdminLogin(adminLogin);
        message.setNumberInPost(0);
        message.setAdminPostMessageType(AdminPostMessageType.START_POST);
        adminPostMessageDao.insertNewMessage(message);
    }

    @Override
    public void addMessage(IAdminPostMessage message){
        validateAdminLogin(message.getAdminLogin());
        if (message.getAdminPostMessageType() == null || message.getNumberInPost() == null){
            throw new IllegalArgumentException("Не передан тип или номер сообщения");
        }

        adminPostMessageDao.insertNewMessage(message);
    }

    @Override
    public List<IAdminPostMessage> getMessages(String chatId){
        String adminLogin = getLoginByChatId(chatId);
        validateAdminLogin(adminLogin);
        return new ArrayList<>(adminPostMessageDao.readAllMessagesByAdmin(adminLogin));
    }

    /**
     * Все идущие подряд текстовые сообщения, склеиваем в одно
     * @param chatId
     * @return
     */
    @Override
    public List<IAdminPostMessage> getCombinedMessages(String chatId){
        List<IAdminPostMessage> fullMessageList = getMessages(chatId);
        List<IAdminPostMessage> combinedMessageList = new ArrayList<>();

        AtomicReference<StringBuilder> messageBuilder = new AtomicReference<>(new StringBuilder());
        AtomicInteger countMessages = new AtomicInteger(1);
        if (fullMessageList.size() > 0){

            String adminLogin = fullMessageList.get(0).getAdminLogin();
            fullMessageList.stream().filter(
                    m -> !AdminPostMessageType.START_POST.equals(m.getAdminPostMessageType())
            ).forEach(m -> {

                if (AdminPostMessageType.TEXT.equals(m.getAdminPostMessageType())) {
                    messageBuilder.get().append(m.getContent() == null ? "" : m.getContent());
                    messageBuilder.get().append(" \n");

                } else {
                    if (messageBuilder.get().length() > 0) {
                        IAdminPostMessage combinedTextMessage = new AdminPostMessage();
                        combinedTextMessage.setNumberInPost(countMessages.get());
                        combinedTextMessage.setAdminPostMessageType(AdminPostMessageType.TEXT);
                        combinedTextMessage.setAdminLogin(adminLogin);
                        combinedTextMessage.setContent(messageBuilder.toString());
                        combinedMessageList.add(combinedTextMessage);
                        countMessages.getAndIncrement();
                        messageBuilder.set(new StringBuilder());
                    }
                }
                if (AdminPostMessageType.PHOTO.equals(m.getAdminPostMessageType())) {
                    IAdminPostMessage combinedTextMessage = new AdminPostMessage();
                    combinedTextMessage.setNumberInPost(countMessages.get());
                    combinedTextMessage.setAdminPostMessageType(AdminPostMessageType.PHOTO);
                    combinedTextMessage.setAdminLogin(adminLogin);
                    combinedTextMessage.setFileId(m.getFileId());
                    combinedMessageList.add(combinedTextMessage);
                    countMessages.getAndIncrement();
                }
            });

            if (messageBuilder.get().length() > 0){
                IAdminPostMessage combinedTextMessage = new AdminPostMessage();
                combinedTextMessage.setNumberInPost(countMessages.get());
                combinedTextMessage.setAdminPostMessageType(AdminPostMessageType.TEXT);
                combinedTextMessage.setAdminLogin(adminLogin);
                combinedTextMessage.setContent(messageBuilder.toString());
                combinedMessageList.add(combinedTextMessage);
                countMessages.getAndIncrement();
            }
        }

        return combinedMessageList;
    }

    @Override
    public void cancelPost(String chatId){
        String adminLogin = getLoginByChatId(chatId);
        validateAdminLogin(adminLogin);
        adminPostMessageDao.deleteAllMessagesByAdmin(adminLogin);
    }



    private void validateAdminLogin(String login){
        if (StringUtils.isEmpty(login))
            throw new IllegalArgumentException("Admin login is empty");
    }

    @Override
    public String getLoginByChatId(String chatId){
        return userService.getAllUsers().stream().filter(igUser -> igUser.getChatId() != null && chatId.equals(igUser.getChatId().toString())).findFirst().orElse(new GUser()).getLogin();

    }

}
