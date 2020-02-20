package so.siva.telegram.bot.got_t_bot.dao.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;

import java.util.List;

public interface IAdminPostMessageDao {
    List<IAdminPostMessage> readAllMessagesByAdmin(String adminLogin);

    void insertNewMessage(IAdminPostMessage message);

    void deleteAllMessagesByAdmin(String adminLogin);
}
