package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.info;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import so.siva.telegram.bot.got_t_bot.config.InfoHouseCardsCommandsConfig;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class InfoHouseCardsCommand extends AInfoCommand {

    @Autowired
    private InfoHouseCardsCommandsConfig infoHouseCardsCommandsConfig;

    private Logger logger = LoggerFactory.getLogger(InfoHouseCardsCommand.class);

    private final static String DECK_A = "a";
    private final static String DECK_B = "b";
    private final static String DECK_VASSAL = Houses.VASSAL.getDomain();

    @Value("${infoHouseCard.imagePlaceholder.fileId}")
    private String placeHolderFileID;

    public InfoHouseCardsCommand(GotBotListenerController gotBotListenerController) {
        super("info_house_cards", "список карт дома", gotBotListenerController,true);
    }

    @Override
    protected SendPhoto startInlineMessageWithPhoto(long chatId) {
        return prepareSendPhoto(placeHolderFileID, chatId)
                .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>(){{
                    add(prepareDecksRow());
                    add(prepareNavigateButtonRow());
                }}));
    }

    @Override
    protected void processUpperLevelBackButtonCallback(Chat chat, Integer messageId, String[] arguments) {
        execute(prepareEditMessagePhoto(
                new ArrayList<>(),
                (InputMediaPhoto) new InputMediaPhoto().setMedia(placeHolderFileID),
                null,
                chat.getId(),
                messageId

        ).setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>(){{
            add(prepareDecksRow());
            add(prepareNavigateButtonRow());
        }})));
    }

    @Override
    protected void processCallbackArguments(Chat chat, Integer messageId, String[] arguments){
        String deckDomainParam = "";
        String houseDomainParam = "";
        String cardDomainParam = "";
        try {
            deckDomainParam = arguments[0];
            houseDomainParam = arguments[1];
            cardDomainParam = arguments[2];
        }catch (IndexOutOfBoundsException e){
            logger.warn(e.toString());
        }

        if (!StringUtils.isEmpty(deckDomainParam) && (DECK_A.equals(deckDomainParam) || DECK_B.equals(deckDomainParam) || DECK_VASSAL.equals(deckDomainParam))){

            List<InfoHouseCardsCommandsConfig.House> houseList = infoHouseCardsCommandsConfig.getDeck().get(deckDomainParam).getHouses();
            String finalHouseDomainParam = houseDomainParam;

            if (Arrays.stream(Houses.values()).noneMatch(houses -> houses.getDomain().equals(finalHouseDomainParam))){

                execute(prepareEditMessagePhoto(
                        prepareHouseButtons(houseList, deckDomainParam),
                        prepareInputMediaPhoto(placeHolderFileID, "Выберите дом"),
                        createBackButton(""),
                        chat.getId(),
                        messageId
                ));
            }else {
                List<InfoHouseCardsCommandsConfig.Card> cardList = houseList.stream().filter(
                        house -> house.getHouseName().equals(finalHouseDomainParam)
                ).findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Ошибка создания списка кнопок домов - не удалось определить наименование дома"))
                        .getCards();

                String finalCardDomainParam = cardDomainParam;
                InfoHouseCardsCommandsConfig.Card cardToShow = cardList.stream()
                        .filter(card1 -> card1.getCardName().equals(finalCardDomainParam))
                        .findFirst().orElse(cardList.get(0));

                execute(prepareEditMessagePhoto(
                        prepareCardsButtons(cardList, deckDomainParam, finalHouseDomainParam),
                        prepareInputMediaPhoto(cardToShow.getFileId(), cardToShow.getCardName()),
                        createBackButton(deckDomainParam),
                        chat.getId(),
                        messageId
                ));
            }
        }
    }

    /**
     * Формируем ряды кнопок для выбора дома
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
            }
            houseButtonRow.add(createButton(buttonLabel, deck + "." + house.getHouseName()));

        });
        rowList.add(houseButtonRow);
        return rowList;
    }

    private List<List<InlineKeyboardButton>> prepareCardsButtons(List<InfoHouseCardsCommandsConfig.Card> cardList, String deck, String houseName){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> cardButtonRow = new ArrayList<>();
        cardList.forEach(card -> {

            String buttonLabel = card.getCardName();
            if (cardButtonRow.size() > 1){
                List<InlineKeyboardButton> copyRow = new ArrayList<>(cardButtonRow);
                rowList.add(copyRow);

                cardButtonRow.clear();
            }
            cardButtonRow.add(createButton(buttonLabel, deck + "." + houseName + "." + buttonLabel));

        });
        rowList.add(cardButtonRow);
        return rowList;
    }


    private List<InlineKeyboardButton> prepareDecksRow(){

        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Набор А", DECK_A));
            add(createButton("Набор Б", DECK_B));
            add(createButton("Другие", DECK_VASSAL));
        }};
    }

    @Override
    protected SendMessage startInlineMessage(long chatId) {
        return null;
    }
}
