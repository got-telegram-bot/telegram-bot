package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.*;


@Component
public class HelpCommand extends ACommand {

    @Autowired
    private List<ACommand> commands;

    public HelpCommand(GotBotListenerController gotBotListenerController) {
        super("help", "Помощь", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());

        StringBuffer helpMessage = new StringBuffer();
        helpMessage.append("Список команд (аргументы перечислены в скобках через пробел): \n\n");
        commands.stream().sorted(Comparator.comparing(BotCommand::getCommandIdentifier)).forEach(aCommand ->
            helpMessage
                    .append("/")
                    .append(aCommand.getCommandIdentifier())
                    .append(" - ")
                    .append(aCommand.getDescription())
                    .append(aCommand.isAdminCommand() ? " [только админ];" : ";")
                    .append(" \n\n")
        );

        helpMessage.append("Доменные сокращения для домов: \n");
        Arrays.stream(Houses.values()).forEach(houses -> helpMessage
                .append(houses.getRusName())
                .append(" - ")
                .append(houses.getDomain())
                .append(";\n")
        );
        helpMessage.append("\n");

        message.setText(helpMessage.toString());
        execute(message);
    }
}
