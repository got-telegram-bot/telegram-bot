package so.siva.telegram.bot.got_t_bot.dao.api;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;


@Repository
@Transactional
public interface IUserDao extends CrudRepository<GUser, String> {
//    GUser readUserByLoginAndPassword(String login, String password);
    GUser findByLoginAndPassword(String login, String password);

//    GUser readUserByChatId(String chatId);

//    void insertNewUser(GUser user);

//    List<GUser> selectAllUsers();

//    void deleteUserByLogin(String login);

//    GUser updateUser(GUser user);
}
