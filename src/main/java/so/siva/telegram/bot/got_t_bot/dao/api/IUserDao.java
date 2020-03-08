package so.siva.telegram.bot.got_t_bot.dao.api;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;

import java.util.List;


@Repository
@Transactional
public interface IUserDao extends CrudRepository<GUser, String> {

    GUser findByLoginAndPassword(String login, String password);

    GUser findByChatId(Long chatId);
}
