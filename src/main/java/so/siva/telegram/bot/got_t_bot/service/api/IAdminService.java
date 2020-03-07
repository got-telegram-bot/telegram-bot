package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;

import java.io.InputStream;
import java.util.List;

public interface IAdminService {


    List<GUser> uploadDdl(InputStream inputStream);

    List<GUser> executeUserDdl(String fileName);
}
