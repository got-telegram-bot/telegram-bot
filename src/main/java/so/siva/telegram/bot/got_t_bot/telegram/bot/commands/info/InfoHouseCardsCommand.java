package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
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
import java.util.concurrent.atomic.AtomicReference;


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
            SendPhoto message = startInlineMessage(chat.getId());
            execute(absSender, message, telegramUser);
        }else {
            Integer messageId = Integer.valueOf(strings[strings.length - 1]);
            if (CLOSE_BUTTON_CALLBACK.equals(strings[0])){

                cancelInfoWindow(absSender, telegramUser, chat, messageId);
                return;
            }
            if (DECK_A.equals(strings[0])){
                if (Arrays.stream(Houses.values()).noneMatch(houses -> houses.getDomain().equals(strings[1]))){

                    EditMessageMedia editMessageMedia = new EditMessageMedia();
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> houses = getHouses(DECK_A);
                    List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();
                    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                    rowList.add(houses);
                    rowList.add(rowActions);
                    markup.setKeyboard(rowList);
                    editMessageMedia.setReplyMarkup(markup);

                    InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
                    inputMediaPhoto.setMedia("AgACAgIAAxkBAAIE0V5iH6z3GRm7uarnW98Ob87SvMF7AAJCrDEb_o8QS4J_YLLMmWuVAoDBDwAEAQADAgADeQADnjEFAAEYBA");
                    inputMediaPhoto.setCaption("Выберите дом");
                    editMessageMedia.setMedia(inputMediaPhoto);
                    editMessageMedia.setChatId(chat.getId());
                    editMessageMedia.setMessageId(messageId);

                    execute(absSender, editMessageMedia, telegramUser);
                    return;
                }

                if (Houses.BARATHEON.getDomain().equals(strings[1])){
                    EditMessageMedia editMessageMedia = new EditMessageMedia();
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> houses = getCards(DECK_A);
                    List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();
                    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                    rowList.add(houses);
                    rowList.add(rowActions);
                    markup.setKeyboard(rowList);
                    editMessageMedia.setReplyMarkup(markup);

                    InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();


                    inputMediaPhoto.setMedia(getCardFileId("Станнис"));

                    if ("Станнис".equals(strings[2])) {
                        inputMediaPhoto.setMedia(getCardFileId("Станнис"));
                    }
                    if ("Давос".equals(strings[2])) {
                        inputMediaPhoto.setMedia(getCardFileId("Давос"));
                    }
                    if ("Пестряк".equals(strings[2])) {
                        inputMediaPhoto.setMedia(getCardFileId("Пестряк"));
                    }

                    inputMediaPhoto.setCaption("Выберите карту");
                    editMessageMedia.setMedia(inputMediaPhoto);
                    editMessageMedia.setChatId(chat.getId());
                    editMessageMedia.setMessageId(messageId);

                    execute(absSender, editMessageMedia, telegramUser);
                    return;

//                    SendMediaGroup sendMediaGroup = new SendMediaGroup();
//                    List<InputMedia> deckAlbumList = new ArrayList<>();

//                    infoHouseCardsCommandsConfig.getDeck().get(DECK_A)
//                            .getHouses()
//                            .stream().filter(house -> house.getHouseName().equals(Houses.BARATHEON.getDomain()))
//                            .forEach(house -> {
//                                house.getCards().forEach(card -> {
//                                    InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
//                                    inputMediaPhoto.setMedia(card.getFileId());
//                                    deckAlbumList.add(inputMediaPhoto);
//                                });
//                            });
//
//                    sendMediaGroup.setMedia(deckAlbumList);
//                    sendMediaGroup.setChatId(chat.getId());
//
//                    execute(absSender, sendMediaGroup, telegramUser);
//                    cancelInfoWindow(absSender, telegramUser, chat, messageId);
//                    return;
                }
            }

        }
    }

    private String getCardFileId(String cardName){
        AtomicReference<String> fileId = new AtomicReference<>("");
        infoHouseCardsCommandsConfig.getDeck().get(DECK_A)
                .getHouses()
                .stream().filter(house -> house.getHouseName().equals(Houses.BARATHEON.getDomain()))
                .forEach(house -> {
                    house.getCards().forEach(card -> {
                        if (card.getCardName().equals(cardName)){
                            fileId.set(card.getFileId());
                        }
                    });
                });
        return fileId.get();
    }



    private List<InlineKeyboardButton> getHouses(String deck){
        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Баратеон",  deck + "." + Houses.BARATHEON.getDomain()));
            add(createButton("Ланнистер",  deck + "." + Houses.LANNISTER.getDomain()));
            add(createButton("Старк",  deck + "." + Houses.STARK.getDomain()));
        }};
    }
    private List<InlineKeyboardButton> getCards(String deck){
        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Станнис",  deck + "." + Houses.BARATHEON.getDomain() + "." + "Станнис"));
            add(createButton("Давос",  deck + "." + Houses.BARATHEON.getDomain() + "." + "Давос"));
            add(createButton("Пестряк",  deck + "." + Houses.BARATHEON.getDomain() + "." + "Пестряк"));
        }};
    }

    private SendPhoto startInlineMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> rowCardType = getCardTypesRow();
        List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(rowCardType);
        rowList.add(rowActions);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendPhoto().setChatId(chatId)
                .setCaption("Выберите комплект: ")
                .setPhoto("AgACAgIAAxkBAAIE0V5iH6z3GRm7uarnW98Ob87SvMF7AAJCrDEb_o8QS4J_YLLMmWuVAoDBDwAEAQADAgADeQADnjEFAAEYBA")
                .setReplyMarkup(inlineKeyboardMarkup);
    }

    private List<InlineKeyboardButton> getCardTypesRow(){

        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Набор А \n (классик)", DECK_A));
            add(createButton("Набор Б \n (пир+танец)", DECK_B));
        }};
    }
}
