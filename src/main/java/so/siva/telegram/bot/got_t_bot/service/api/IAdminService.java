package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;

import java.io.InputStream;
import java.util.List;

public interface IAdminService {


    List<IGUser> uploadDdl(InputStream inputStream);

    List<IGUser> executeUserDdl(String fileName);
}
