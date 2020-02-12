package so.siva.telegram.bot.got_t_bot.service.house.random;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IPlayerStartList;

public interface IHouseRandomizerService {
    String randomizePlayers(IPlayerStartList playerStartList);
}
