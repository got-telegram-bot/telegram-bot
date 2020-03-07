package so.siva.telegram.bot.got_t_bot.dao.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;

import java.util.List;

public interface IAdminPostMessageDao {
    List<AdminPostMessage> readAllMessagesByAdmin(String adminLogin);

    void insertNewMessage(AdminPostMessage message);

    void deleteAllMessagesByAdmin(String adminLogin);
}
