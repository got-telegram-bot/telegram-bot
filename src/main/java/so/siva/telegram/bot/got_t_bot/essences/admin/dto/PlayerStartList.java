package so.siva.telegram.bot.got_t_bot.essences.admin.dto;

import so.siva.telegram.bot.got_t_bot.essences.admin.dto.api.IPlayerStartList;
import so.siva.telegram.bot.got_t_bot.core.Houses;

import java.util.List;

public class PlayerStartList implements IPlayerStartList {
    private List<String> playersList;
    private List<Houses> excludedHouses;

    @Override
    public List<String> getPlayersList() {
        return playersList;
    }

    @Override
    public void setPlayersList(List<String> playersList) {
        this.playersList = playersList;
    }

    @Override
    public List<Houses> getExcludedHouses() {
        return excludedHouses;
    }

    @Override
    public void setExcludedHouses(List<Houses> excludeHouses) {
        this.excludedHouses = excludeHouses;
    }
}
