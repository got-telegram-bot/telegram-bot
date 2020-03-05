package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.config.InfoHouseCardsCommandsConfig;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class InfoHouseCardsCommand extends AInfoCommand {

    @Autowired
    private InfoHouseCardsCommandsConfig infoHouseCardsCommandsConfig;

    private final static String DECK_A = "a";
    private final static String DECK_B = "b";

    public InfoHouseCardsCommand(GotBotListenerController gotBotListenerController) {
        super("info_house_cards", "список карт дома", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {

        if (strings.length == 0){
            SendMessage message = startInlineMessage(chat.getId());
            execute(absSender, message, telegramUser);
        }else {
            Integer messageId = Integer.valueOf(strings[strings.length - 1]);
            if (CLOSE_BUTTON_CALLBACK.equals(strings[0])){

                cancelInfoWindow(absSender, telegramUser, chat, messageId);
                return;
            }
            if (DECK_A.equals(strings[0])){
                if (Arrays.stream(Houses.values()).noneMatch(houses -> houses.getDomain().equals(strings[1]))){

                    EditMessageText editMessageText = new EditMessageText();
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> houses = getHouses(DECK_A);
                    List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();
                    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                    rowList.add(houses);
                    rowList.add(rowActions);
                    markup.setKeyboard(rowList);
                    editMessageText.setReplyMarkup(markup);
                    editMessageText.setText("Выберите Дом: ");
                    editMessageText.setChatId(chat.getId());
                    editMessageText.setMessageId(messageId);

                    execute(absSender, editMessageText, telegramUser);
                    return;
                }

                if (Houses.BARATHEON.getDomain().equals(strings[1])){
                    SendMediaGroup sendMediaGroup = new SendMediaGroup();
                    List<InputMedia> deckAlbumList = new ArrayList<>();

                    infoHouseCardsCommandsConfig.getDeck().get(DECK_A)
                            .getHouses()
                            .stream().filter(house -> house.getHouseName().equals(Houses.BARATHEON.getDomain()))
                            .forEach(house -> {
                                house.getCards().forEach(card -> {
                                    InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
                                    inputMediaPhoto.setMedia(card.getFileId());
                                    deckAlbumList.add(inputMediaPhoto);
                                });
                            });

                    sendMediaGroup.setMedia(deckAlbumList);
                    sendMediaGroup.setChatId(chat.getId());

                    execute(absSender, sendMediaGroup, telegramUser);
                    cancelInfoWindow(absSender, telegramUser, chat, messageId);
                    return;
                }
            }

        }
    }



    private List<InlineKeyboardButton> getHouses(String deck){
        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Баратеон",  deck + "." + Houses.BARATHEON.getDomain()));
            add(createButton("Ланнистер",  deck + "." + Houses.LANNISTER.getDomain()));
            add(createButton("Старк",  deck + "." + Houses.STARK.getDomain()));
        }};
    }

    private SendMessage startInlineMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> rowCardType = getCardTypesRow();
        List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(rowCardType);
        rowList.add(rowActions);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите комплект: ").setReplyMarkup(inlineKeyboardMarkup);
    }

    private List<InlineKeyboardButton> getCardTypesRow(){

        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Набор А \n (классик)", DECK_A));
            add(createButton("Набор Б \n (пир+танец)", DECK_B));
        }};
    }
}
