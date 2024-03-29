package so.siva.telegram.bot.got_t_bot.essences.users;

import java.util.List;

public interface IUserService {
    GUser getUserByLoginAndPassword(GUser user);

    GUser signUpUser(GUser userForSignUp);

    List<GUser> getAllUsers();

    List<GUser> getAllUsersPageableByOne(int pageNumber);

    List<GUser> getAllPlayers();

    List<GUser> getAdmins();

    List<GUser> getUsersForReadyCheck();

    GUser getUserByChatId(Long chatId);

    GUser authorizeUser(GUser user, Long chatId);

    GUser updateUser(GUser user);

    List<GUser> deleteUser(String login);
}
