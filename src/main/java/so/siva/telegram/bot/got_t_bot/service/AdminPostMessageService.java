package so.siva.telegram.bot.got_t_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.essences.admin.IAdminPostMessageDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminPostMessageService;
import so.siva.telegram.bot.got_t_bot.essences.users.IUserService;

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
        AdminPostMessage message = new AdminPostMessage();
        message.setAdminLogin(adminLogin);
        message.setNumberInPost(0);
        message.setAdminPostMessageType(AdminPostMessageType.START_POST);
        adminPostMessageDao.save(message);
    }

    @Override
    public void addMessage(AdminPostMessage message){
        validateAdminLogin(message.getAdminLogin());
        if (message.getAdminPostMessageType() == null || message.getNumberInPost() == null){
            throw new IllegalArgumentException("Не передан тип или номер сообщения");
        }

        adminPostMessageDao.save(message);
    }

    @Override
    public List<AdminPostMessage> getMessages(String chatId){
        String adminLogin = getLoginByChatId(chatId);
        validateAdminLogin(adminLogin);
        return new ArrayList<>(adminPostMessageDao.findAllByAdminLogin(adminLogin));
    }

    /**
     * Все идущие подряд текстовые сообщения, склеиваем в одно
     * @param chatId
     * @return
     */
    @Override
    public List<AdminPostMessage> getCombinedMessages(String chatId){
        List<AdminPostMessage> fullMessageList = getMessages(chatId);
        List<AdminPostMessage> combinedMessageList = new ArrayList<>();

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
                        AdminPostMessage combinedTextMessage = new AdminPostMessage();
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
                    AdminPostMessage combinedTextMessage = new AdminPostMessage();
                    combinedTextMessage.setNumberInPost(countMessages.get());
                    combinedTextMessage.setAdminPostMessageType(AdminPostMessageType.PHOTO);
                    combinedTextMessage.setAdminLogin(adminLogin);
                    combinedTextMessage.setFileId(m.getFileId());
                    combinedMessageList.add(combinedTextMessage);
                    countMessages.getAndIncrement();
                }
            });

            if (messageBuilder.get().length() > 0){
                AdminPostMessage combinedTextMessage = new AdminPostMessage();
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
        adminPostMessageDao.deleteAllByAdminLogin(adminLogin);
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
