package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;

import java.util.List;

public interface IUserService {
    GUser getUserByLoginAndPassword(GUser user);

    GUser signUpUser(GUser userForSignUp);

    List<GUser> getAllUsers();

    GUser authorizeUser(GUser user, Long chatId);

    GUser updateUser(GUser user);

    List<GUser> deleteUser(String login);
}
