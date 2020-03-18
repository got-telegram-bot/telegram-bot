package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.info;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.admin.AAdminCommand;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.player.APlayerCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class HelpCommand extends AInfoCommand {

    @Autowired
    private List<ACommand> commands;

    @Autowired
    private List<AAdminCommand> adminCommands;

    @Autowired
    private List<APlayerCommand> playerCommands;

    private final String ADMIN_COMMANDS = "admin";
    private final String PLAYER_COMMANDS = "player";

    public HelpCommand(GotBotListenerController gotBotListenerController) {
        super("help", "Помощь", gotBotListenerController, false);
    }


    @Override
    protected SendMessage startInlineMessage(long chatId) {
        return responseProducer.prepareSendMessage(prepareHelpMessage(getFilteredCommandList()), chatId)
                .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>(){{
                    add(prepareCommandsRow());
                    add(prepareNavigateButtonRow());
                }}));
    }
    @Override
    protected void processCallbackArguments(Chat chat, Integer messageId, String[] arguments) {
        List<ACommand> filteredCommandList = new ArrayList<>(getFilteredCommandList());

        if (Arrays.asList(arguments).contains(ADMIN_COMMANDS)){
            filteredCommandList = new ArrayList<>(adminCommands);
        }
        if (Arrays.asList(arguments).contains(PLAYER_COMMANDS)){
            filteredCommandList = new ArrayList<>(playerCommands);
        }

        execute(prepareEditMessageText(
                prepareCommandsButtons(),
                prepareHelpMessage(filteredCommandList),
                null,
                chat.getId(),
                messageId

        ).setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>(){{
            add(prepareCommandsRow());
            add(prepareNavigateButtonRow());
        }})));

    }

    private String prepareHelpMessage(List<ACommand> commands){
        StringBuffer helpMessage = new StringBuffer();
        helpMessage.append("Список команд (аргументы перечислены в скобках через пробел): \n\n");
        commands.stream().sorted(Comparator.comparing(BotCommand::getCommandIdentifier)).forEach(aCommand ->
                helpMessage
                        .append("/")
                        .append(aCommand.getCommandIdentifier())
                        .append(" - ")
                        .append(aCommand.getDescription())
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
        return helpMessage.toString();
    }

    private List<List<InlineKeyboardButton>> prepareCommandsButtons(){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(prepareCommandsRow());
        return rowList;
    }
    private List<InlineKeyboardButton> prepareCommandsRow(){
        return new ArrayList<InlineKeyboardButton>(){{
            add(markupProducer.createButton("Общие", ""));
            add(markupProducer.createButton("Игрок", PLAYER_COMMANDS));
            add(markupProducer.createButton("Админ", ADMIN_COMMANDS));
        }};
    }

    private List<ACommand> getFilteredCommandList(){
        return commands.stream()
                .filter(command -> !(command instanceof AAdminCommand))
                .filter(command -> !(command instanceof APlayerCommand))
                .collect(Collectors.toList());
    }

    @Override
    protected void processUpperLevelBackButtonCallback(Chat chat, Integer messageId, String[] arguments) {

    }

    @Override
    protected SendPhoto startInlineMessageWithPhoto(long chatId) {
        return null;
    }

}
