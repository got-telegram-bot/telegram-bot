package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;

import java.util.List;

public interface IAdminPostMessageService {
    void startPost(String chatId);

    void addMessage(AdminPostMessage message);

    List<AdminPostMessage> getMessages(String chatId);

    List<AdminPostMessage> getCombinedMessages(String chatId);

    void cancelPost(String chatId);

    String getLoginByChatId(String chatId);
}
