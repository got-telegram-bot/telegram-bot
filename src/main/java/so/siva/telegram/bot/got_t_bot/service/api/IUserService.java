package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;

import java.util.List;

public interface IUserService {
    IGUser getUserByLoginAndPassword(IGUser user);

    IGUser signUpUser(IGUser userForSignUp);

    List<IGUser> getAllUsers();

    IGUser authorizeUser(IGUser user, Long chatId);

    IGUser updateUser(IGUser user);

    List<IGUser> deleteUser(String login);
}
