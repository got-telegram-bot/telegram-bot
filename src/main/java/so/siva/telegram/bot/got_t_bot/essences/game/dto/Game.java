package so.siva.telegram.bot.got_t_bot.essences.game.dto;

import so.siva.telegram.bot.got_t_bot.core.GameType;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;

import java.util.List;

public class Game {

    private String chatId;
    private String chatTitle;
    private GameType gameType;
    private String decks;
    private int turnNumber;
    private String gameFieldLink;
    private List<GUser> users;
    private List<GUser> players;




}
