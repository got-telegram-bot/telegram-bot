package so.siva.telegram.bot.got_t_bot.dao.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;

import java.util.List;

public interface IUserDao {
    IUser readUserByLoginAndPassword(String login, String password);

    void insertNewUser(IUser user);

    List<IUser> selectAllUsers();

    void deleteUserByLogin(String login);

    IUser updateUser(IUser user);
}
