package so.siva.telegram.bot.got_t_bot.dao.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;

import java.util.List;

public interface IUserDao {
    GUser readUserByLoginAndPassword(String login, String password);

    GUser readUserByChatId(String chatId);

    void insertNewUser(GUser user);

    List<GUser> selectAllUsers();

    void deleteUserByLogin(String login);

    GUser updateUser(GUser user);
}
