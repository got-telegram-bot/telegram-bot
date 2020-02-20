package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;

import java.util.List;

public interface IAdminPostMessageService {
    void startPost(String chatId);

    void addMessage(IAdminPostMessage message);

    List<IAdminPostMessage> getMessages(String chatId);

    void cancelPost(String chatId);

    String getLoginByChatId(String chatId);
}
