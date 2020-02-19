package so.siva.telegram.bot.got_t_bot.dao.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;

import java.util.List;

public interface IUserDao {
    IGUser readUserByLoginAndPassword(String login, String password);

    IGUser readUserByChatId(String chatId);

    void insertNewUser(IGUser user);

    List<IGUser> selectAllUsers();

    void deleteUserByLogin(String login);

    IGUser updateUser(IGUser user);
}
