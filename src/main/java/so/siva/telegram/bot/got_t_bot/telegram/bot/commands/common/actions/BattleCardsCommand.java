package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.actions;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
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

        //череп в юникоде
        add("0 + \uD83D\uDC80");
        add("0 + \uD83D\uDC80");

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


    public BattleCardsCommand(GotBotListenerController gotBotListenerController) {
        super("battle_cards", "выдать карты перевеса", gotBotListenerController);
    }

    @Override
    public void execute(Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());

        int attacker = new Random().nextInt(battleCards.size() -1);
        int defender;
        do {
            defender = new Random().nextInt(battleCards.size() -1);
        }while (defender == attacker);

        message.setText("Нападающий: " + battleCards.get(attacker) + ", Защищающийся: " + battleCards.get(defender));
        execute(message);
    }
}
