package so.siva.telegram.bot.got_t_bot.essences.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;

import java.util.List;

@Repository
@Transactional
public interface IAdminPostMessageDao extends CrudRepository<AdminPostMessage, AdminPostMessage.NumberAndLoginPrimaryKey> {

    List<AdminPostMessage> findAllByAdminLogin(String adminLogin);

    void deleteAllByAdminLogin(String adminLogin);
}
