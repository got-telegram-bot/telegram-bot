package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@Component
public class InfoHouseCardsCommand extends AInfoCommand {

    @Autowired
    private InfoHouseCardsCommandsConfig infoHouseCardsCommandsConfig;

    private Logger logger = LoggerFactory.getLogger(InfoHouseCardsCommand.class);

    private final static String DECK_A = "a";
    private final static String DECK_B = "b";
    private final static String placeHolderFileID = "AgACAgIAAxkBAAIE0V5iH6z3GRm7uarnW98Ob87SvMF7AAJCrDEb_o8QS4J_YLLMmWuVAoDBDwAEAQADAgADeQADnjEFAAEYBA";

    public InfoHouseCardsCommand(GotBotListenerController gotBotListenerController) {
        super("info_house_cards", "список карт дома", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {

        if (strings.length == 0){
            SendPhoto message = startInlineMessage(chat.getId());
            execute(absSender, message, telegramUser);
        }else {

            String deckDomainParam = "";
            String houseDomainParam = "";
            String cardDomainParam = "";
            Integer messageId = Integer.valueOf(strings[strings.length - 1]);
            try {
                deckDomainParam = strings[0];
                houseDomainParam = strings[1];
                cardDomainParam = strings[2];
            }catch (IndexOutOfBoundsException e){
                logger.warn(e.getMessage());
            }

            if (CLOSE_BUTTON_CALLBACK.equals(strings[0])){

                cancelInfoWindow(absSender, telegramUser, chat, messageId);
                return;
            }
            if (DECK_A.equals(deckDomainParam)){

                List<InfoHouseCardsCommandsConfig.House> houseList = infoHouseCardsCommandsConfig.getDeck().get(DECK_A).getHouses();

                String finalHouseDomainParam = houseDomainParam;
                if (Arrays.stream(Houses.values()).noneMatch(houses -> houses.getDomain().equals(finalHouseDomainParam))){

                    EditMessageMedia editMessageMedia = new EditMessageMedia();
                    InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                    List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();
                    List<List<InlineKeyboardButton>> rowList = new ArrayList<>(prepareHouseButtons(houseList, DECK_A));
                    rowList.add(rowActions);
                    markup.setKeyboard(rowList);
                    editMessageMedia.setReplyMarkup(markup);

                    editMessageMedia.setMedia(prepareInputMediaPhoto(placeHolderFileID, "Выберите дом"));
                    editMessageMedia.setChatId(chat.getId());
                    editMessageMedia.setMessageId(messageId);

                    execute(absSender, editMessageMedia, telegramUser);
                    return;
                }else {
                    if (Houses.BARATHEON.getDomain().equals(houseDomainParam)){
                        List<InfoHouseCardsCommandsConfig.Card> cardList = houseList.stream().filter(
                                house -> house.getHouseName().equals(Houses.BARATHEON.getDomain())
                        ).findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Ошибка создания списка кнопок домов - не удалось определить наименование дома"))
                                .getCards();

                        EditMessageMedia editMessageMedia = new EditMessageMedia();
                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                        List<InlineKeyboardButton> rowActions = prepareCloseButtonRow();
                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>(prepareCardsButtons(cardList, DECK_A, Houses.BARATHEON.getDomain()));
                        rowList.add(rowActions);
                        markup.setKeyboard(rowList);
                        editMessageMedia.setReplyMarkup(markup);

                        String finalCardDomainParam = cardDomainParam;
                        InfoHouseCardsCommandsConfig.Card card = cardList.stream()
                                .filter(card1 -> card1.getCardName().equals(finalCardDomainParam))
                                .findFirst().orElse(cardList.get(0));

                        InputMediaPhoto inputMediaPhoto = prepareInputMediaPhoto( card.getFileId(), card.getCardName());

                        editMessageMedia.setMedia(inputMediaPhoto);
                        editMessageMedia.setChatId(chat.getId());
                        editMessageMedia.setMessageId(messageId);

                        execute(absSender, editMessageMedia, telegramUser);
                        return;
                    }
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


    /**
     * Формируем ряды кнопок для выбора дома
     * @param houseList
     * @param deck
     * @return
     */
    private List<List<InlineKeyboardButton>> prepareHouseButtons(List<InfoHouseCardsCommandsConfig.House> houseList, String deck){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> houseButtonRow = new ArrayList<>();
        houseList.forEach(house -> {

            String buttonLabel = Arrays.stream(Houses.values())
                    .filter(housesFromEnum ->
                            housesFromEnum.getDomain().equals(house.getHouseName())
                    ).findFirst()
                    .orElseThrow(
                            () -> new IllegalArgumentException("Ошибка создания списка кнопок домов - не удалось определить наименование дома")
                    ).getRusName();
            if (houseButtonRow.size() > 2){
                List<InlineKeyboardButton> copyRow = new ArrayList<>(houseButtonRow);
                rowList.add(copyRow);

                houseButtonRow.clear();

                houseButtonRow.add(createButton(buttonLabel, deck + "." + house.getHouseName()));
            }else {
                houseButtonRow.add(createButton(buttonLabel, deck + "." + house.getHouseName()));
            }

        });
        rowList.add(houseButtonRow);
        return rowList;
    }

    private List<List<InlineKeyboardButton>> prepareCardsButtons(List<InfoHouseCardsCommandsConfig.Card> cardList, String deck, String houseName){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> houseButtonRow = new ArrayList<>();
        cardList.forEach(card -> {

            String buttonLabel = card.getCardName();
            if (houseButtonRow.size() > 2){
                List<InlineKeyboardButton> copyRow = new ArrayList<>(houseButtonRow);
                rowList.add(copyRow);

                houseButtonRow.clear();

                houseButtonRow.add(createButton(buttonLabel, deck + "." + houseName + "." + buttonLabel));
            }else {
                houseButtonRow.add(createButton(buttonLabel, deck + "." + houseName + "." + buttonLabel));
            }

        });
        rowList.add(houseButtonRow);
        return rowList;
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
