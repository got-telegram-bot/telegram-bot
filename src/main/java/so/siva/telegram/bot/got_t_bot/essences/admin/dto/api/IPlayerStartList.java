package so.siva.telegram.bot.got_t_bot.essences.admin.dto.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import so.siva.telegram.bot.got_t_bot.essences.admin.dto.PlayerStartList;
import so.siva.telegram.bot.got_t_bot.core.Houses;

import java.util.List;


@JsonDeserialize(as = PlayerStartList.class)
public interface IPlayerStartList {

    List<String> getPlayersList();

    void setPlayersList(List<String> playersList);

    List<Houses> getExcludedHouses();

    void setExcludedHouses(List<Houses> excludeHouses);
}
