package so.siva.telegram.bot.got_t_bot.essences.admin.random;

import so.siva.telegram.bot.got_t_bot.essences.admin.dto.api.IPlayerStartList;

public interface IHouseRandomizerService {
    String randomizePlayers(IPlayerStartList playerStartList);
}
