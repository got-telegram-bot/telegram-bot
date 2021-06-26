package so.siva.telegram.bot.got_t_bot.essences.users;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IUserDao extends PagingAndSortingRepository<GUser, String> {

    GUser findByLoginAndPassword(String login, String password);

    GUser findByChatId(Long chatId);

    List<GUser> findByHouseIsNotNull();
}
