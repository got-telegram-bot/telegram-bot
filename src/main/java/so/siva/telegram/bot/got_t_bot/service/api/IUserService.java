package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;

import java.util.List;

public interface IUserService {
    IUser getUserByLoginAndPassword(IUser user);

    IUser signUpUser(IUser userForSignUp);

    List<IUser> getAllUsers();

    IUser authorizeUser(IUser user, Long chatId);

    IUser updateUser(IUser user);

    List<IUser> deleteUser(String login);
}
