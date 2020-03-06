package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
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
        //Если попали сюда первый раз, то подготавливаем сообщение
        if (strings.length == 0){
            SendPhoto message = startInlineMessage(chat.getId());
            execute(absSender, message, telegramUser);
            return;
        }
        //Параметр messageId добавляет главный контроллер при парсинге пришедшего колбека при нажатии на кнопку
        Integer messageId = Integer.valueOf(strings[strings.length - 1]);

        //Если нажали кнопку назад из следующего по вложенности меню, попадаем сюда
        //Формируем сообщение аналогично первому попаданию в команду
        if (BACK_BUTTON_CALLBACK.equals(strings[0])){
            execute(absSender, prepareEditMessageMedia(
                    new ArrayList<>(),
                    (InputMediaPhoto) new InputMediaPhoto().setMedia(placeHolderFileID),
                    null,
                    chat.getId(),
                    messageId

            ).setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>(){{
                add(prepareCardTypesRow());
                add(prepareNavigateButtonRow());
            }})), telegramUser);
            return;
        }

        if (Arrays.asList(strings).contains(CLOSE_BUTTON_CALLBACK)){
            cancelInfoWindow(absSender, telegramUser, chat, messageId);
            return;
        }

        String deckDomainParam = "";
        String houseDomainParam = "";
        String cardDomainParam = "";
        try {
            deckDomainParam = strings[0];
            houseDomainParam = strings[1];
            cardDomainParam = strings[2];
        }catch (IndexOutOfBoundsException e){
            logger.warn(e.toString());
        }

        if (!StringUtils.isEmpty(deckDomainParam) && (DECK_A.equals(deckDomainParam) || DECK_B.equals(deckDomainParam))){

            List<InfoHouseCardsCommandsConfig.House> houseList = infoHouseCardsCommandsConfig.getDeck().get(deckDomainParam).getHouses();
            String finalHouseDomainParam = houseDomainParam;

            if (Arrays.stream(Houses.values()).noneMatch(houses -> houses.getDomain().equals(finalHouseDomainParam))){

                execute(absSender, prepareEditMessageMedia(
                        prepareHouseButtons(houseList, deckDomainParam),
                        prepareInputMediaPhoto(placeHolderFileID, "Выберите дом"),
                        createBackButton(""),
                        chat.getId(),
                        messageId
                ), telegramUser);
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

                execute(absSender, prepareEditMessageMedia(
                        prepareCardsButtons(cardList, deckDomainParam, finalHouseDomainParam),
                        prepareInputMediaPhoto(cardToShow.getFileId(), cardToShow.getCardName()),
                        createBackButton(deckDomainParam),
                        chat.getId(),
                        messageId
                ), telegramUser);
            }
        }

    }

    private EditMessageMedia prepareEditMessageMedia(
            List<List<InlineKeyboardButton>> commonRowList,
            InputMediaPhoto inputMediaPhoto,
            InlineKeyboardButton backButton,
            Long chatId, Integer messageId
    ){
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        List<List<InlineKeyboardButton>> completeRowList = new ArrayList<>(commonRowList);
        completeRowList.add(prepareNavigateButtonRow(backButton));
        editMessageMedia.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(completeRowList));
        editMessageMedia.setMedia(inputMediaPhoto);
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(messageId);
        return editMessageMedia;
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

    private SendPhoto startInlineMessage(long chatId) {
        return new SendPhoto().setChatId(chatId)
                .setPhoto(placeHolderFileID)
                .setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>(){{
                    add(prepareCardTypesRow());
                    add(prepareNavigateButtonRow());
                }}));
    }

    private List<InlineKeyboardButton> prepareCardTypesRow(){

        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton("Набор А (классик)", DECK_A));
            add(createButton("Набор Б (пир+танец)", DECK_B));
        }};
    }
}
