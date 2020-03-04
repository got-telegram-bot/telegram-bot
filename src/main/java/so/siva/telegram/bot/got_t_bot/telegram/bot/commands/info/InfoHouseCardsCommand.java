package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.List;


@Component
public class InfoHouseCardsCommand extends ACommand {


    public InfoHouseCardsCommand(GotBotListenerController gotBotListenerController) {
        super("info_cards", "информация по картам ", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {

        if (strings.length == 0){
            SendMessage message = startInlineMessage(chat.getId());
            execute(absSender, message, telegramUser);
        }else {
            if ("cancel".equals(strings[0])){

                cancel(absSender, chat, telegramUser, strings);
                return;
            }
            if ("house_cards".equals(strings[0])){

                EditMessageText editMessageText = new EditMessageText();
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                List<InlineKeyboardButton> houses = getHouses();
                List<InlineKeyboardButton> rowActions = getActionsRow();
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(houses);
                rowList.add(rowActions);
                markup.setKeyboard(rowList);
                editMessageText.setReplyMarkup(markup);
                editMessageText.setText("Дом: ");
                editMessageText.setChatId(chat.getId());
                editMessageText.setMessageId(Integer.valueOf(strings[strings.length - 1]));

                execute(absSender, editMessageText, telegramUser);
            }
            if (Houses.BARATHEON.getDomain().equals(strings[0])){
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setPhoto("AgACAgIAAxkBAAIEUl5fwpSmYCmnG5-3GuXa6SSixw5zAALlrjEbYyf4SuBXxRWLR9_CSsZ-kS4AAwEAAwIAA3gAAwKRAAIYBA");
                sendPhoto.setChatId(chat.getId());

                execute(absSender, sendPhoto, telegramUser);
                cancel(absSender, chat, telegramUser, strings);
                return;
            }
        }
    }

    private void cancel(AbsSender absSender, Chat chat, User telegramUser, String[] strings){

        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chat.getId());
        deleteMessage.setMessageId(Integer.valueOf(strings[strings.length - 1]));
        execute(absSender, deleteMessage, telegramUser);
    }

    private List<InlineKeyboardButton> getHouses(){
        return new ArrayList<InlineKeyboardButton>(){{
            add(prepareButton("Баратеон", getCommandIdentifier() + "." + Houses.BARATHEON.getDomain()));
        }};
    }


    private SendMessage startInlineMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> rowCardType = getCardTypesRow();
        List<InlineKeyboardButton> rowActions = getActionsRow();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(rowCardType);
        rowList.add(rowActions);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите набор карт: ").setReplyMarkup(inlineKeyboardMarkup);
    }

    private List<InlineKeyboardButton> getActionsRow(){
        return new ArrayList<InlineKeyboardButton>(){{
            add(prepareButton("Выход", getCommandIdentifier() + "." + "cancel"));
        }};
    }

    private List<InlineKeyboardButton> getCardTypesRow(){

        return new ArrayList<InlineKeyboardButton>(){{
            add(prepareButton("Карты Дома", getCommandIdentifier() + "." + "house_cards"));
//            add(prepareButton("Карты Вестероса (пусто)", getCommandIdentifier() + "." + "westeros_cards"));
//            add(prepareButton("Карты Одичалых (пусто)", getCommandIdentifier() + "." + "wildlings_cards"));
        }};
    }

    private InlineKeyboardButton prepareButton(String text, String callBackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBackData);

        return button;
    }
}
