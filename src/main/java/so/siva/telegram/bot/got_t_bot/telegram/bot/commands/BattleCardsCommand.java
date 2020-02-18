package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@Component
public class BattleCardsCommand extends ACommand {

    private final List<String> battleCards = new ArrayList<String>(){{
        add("0");
        add("0");
        add("0");
        add("0");

        add("0");
        add("0");
        add("0");
        add("0");

        add("0 + череп");
        add("0 + череп");

        add("1 + меч");
        add("1 + меч");
        add("1 + меч");
        add("1 + меч");

        add("1 + башня");
        add("1 + башня");
        add("1 + башня");
        add("1 + башня");

        add("2");
        add("2");
        add("2");
        add("2");

        add("3");
        add("3");
    }};


    public BattleCardsCommand() {
        super("battle_cards", "выдать карты перевеса \n");
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());

        int attacker = new Random().nextInt(battleCards.size() -1);
        int defender;
        do {
            defender = new Random().nextInt(battleCards.size() -1);
        }while (defender == attacker);

        message.setText("Нападающий: " + battleCards.get(attacker) + ", Защищающийся: " + battleCards.get(defender));
        execute(absSender, message, telegramUser);
    }
}
