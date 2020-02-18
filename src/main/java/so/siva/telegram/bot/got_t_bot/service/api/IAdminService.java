package so.siva.telegram.bot.got_t_bot.service.api;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.ITestDto;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;

import java.io.InputStream;
import java.util.List;

public interface IAdminService {


    List<IUser> uploadDdl(InputStream inputStream);

    List<IUser> executeUserDdl(String fileName);
}
