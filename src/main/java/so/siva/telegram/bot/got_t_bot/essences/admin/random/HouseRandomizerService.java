package so.siva.telegram.bot.got_t_bot.essences.admin.random;

import org.springframework.stereotype.Service;
import so.siva.telegram.bot.got_t_bot.essences.admin.dto.api.IPlayerStartList;
import so.siva.telegram.bot.got_t_bot.core.Houses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static so.siva.telegram.bot.got_t_bot.core.Houses.*;

@Service
public class HouseRandomizerService implements IHouseRandomizerService {

    private final List<Houses> HOUSE_LIST = new ArrayList<Houses>(){{
        add(BARATHEON);
        add(LANNISTER);
        add(STARK);
        add(GREYJOY);
        add(TYRELL);
        add(MARTELL);
        add(ARRYN);
        add(TARGARIEN);
    }};

    /**
     * Рандомно раздать игрокам дома.
     * Игроку можно задать конкретный дом, передав его в составе имени в круглых скобках прим. "Ходов (LANNISTER)"
     * Можно исключить какие-либо дома
     * @param playerStartList
     * @return
     */
    @Override
    public String randomizePlayers(IPlayerStartList playerStartList){
        StringBuilder finalPlayersList = new StringBuilder();

        //получаем список игроков и список домов, которые нужно исключить
        List<String> playerList = playerStartList.getPlayersList();
        List<Houses> excludedHousesList = playerStartList.getExcludedHouses();

        List<String> explicitPlayersList = new ArrayList<>();

        //если игроку явно задали дом, исключаем игрока из общего списка, дом добавляем в список исключенных
        Iterator<String> playersIterator = playerList.iterator();
        for (Iterator<String> it = playersIterator; it.hasNext(); ) {
            final String playerName = it.next();
            if (playerName.contains("(") && playerName.contains(")")){
                String houseName = playerName.substring(playerName.indexOf("(") + 1, playerName.indexOf(")"));
                String playerCuttedName = playerName.substring(0, playerName.indexOf("("));
                houseName = houseName.trim();
                Houses explicitHouse = Houses.valueOf(houseName);
                if (HOUSE_LIST.contains(explicitHouse)){
                    excludedHousesList.add(explicitHouse);
                    explicitPlayersList.add(playerCuttedName + ": " + explicitHouse.getRusName());
                    it.remove();
                }
            }
        }

        //случайным образом раздаем игрокам дома
        List<String> filteredHouseList = HOUSE_LIST.stream().filter(house -> !excludedHousesList.contains(house)).map(Houses::getRusName).collect(Collectors.toList());
        Collections.shuffle(filteredHouseList);
        if (playerList.size() < filteredHouseList.size()){
            Iterator finalHouseIterator = filteredHouseList.iterator();

            for (String players : playerList){
                finalPlayersList.append(players).append(": ").append(finalHouseIterator.next()).append("\n");
            }
            //в конец списка добавляем игроков с явно заданными домами
            for (String explicitPlayer : explicitPlayersList){
                finalPlayersList.append(explicitPlayer).append("\n");
            }


        }else throw new IllegalArgumentException("Number of players not match to final house count");




        return finalPlayersList.toString();
    }

}
