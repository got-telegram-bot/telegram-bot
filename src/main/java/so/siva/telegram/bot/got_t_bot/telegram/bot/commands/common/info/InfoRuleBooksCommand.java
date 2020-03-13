package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.info;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class InfoRuleBooksCommand extends AInfoCommand {


    public InfoRuleBooksCommand(GotBotListenerController gotBotListenerController) {
        super("info_rule_books", "инфо со ссылками на правила игры ", gotBotListenerController);
    }

    @Override
    public void execute(Chat chat, String[] strings) {

        if (Arrays.asList(strings).contains(CLOSE_BUTTON_CALLBACK)){
            Integer messageId = Integer.valueOf(strings[strings.length - 1]);
            cancelInfoMessage(chat, messageId);
            return;
        }

        SendMessage message = prepareInlineKeyBoardMessage(chat.getId());
        execute(message);
    }

    private SendMessage prepareInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton rulesClassicLinkModButton = new InlineKeyboardButton();
        InlineKeyboardButton rulesModLinkButton = new InlineKeyboardButton();
        InlineKeyboardButton faqV2LinkButton = new InlineKeyboardButton();

        rulesClassicLinkModButton.setText("Правила: Classic");
        rulesClassicLinkModButton.setUrl("https://drive.google.com/file/d/1tcTJIgu-ddVL30hNuNCaEL1iL0tKg7WE/view");

        rulesModLinkButton.setText("Правила: Mother of Dragons");
        rulesModLinkButton.setUrl("https://drive.google.com/file/d/1u8AjUd7iMjrSVebP2rw9QM-S5LUFZuKa/view");

        faqV2LinkButton.setText("FAQ v2.0");
        faqV2LinkButton.setUrl("https://drive.google.com/file/d/1i-ttKmmYORm3pCzLWT2nyDVeslw7Raga/view");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        keyboardButtonsRow1.add(rulesClassicLinkModButton);
        keyboardButtonsRow1.add(rulesModLinkButton);
        keyboardButtonsRow2.add(faqV2LinkButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(prepareNavigateButtonRow());

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Ссылки: ").setReplyMarkup(inlineKeyboardMarkup);
    }


    @Override
    protected SendMessage startInlineMessage(long chatId) {
        return prepareInlineKeyBoardMessage(chatId);
    }

    @Override
    protected void processUpperLevelBackButtonCallback(Chat chat, Integer messageId, String[] arguments) {
    }

    @Override
    protected void processCallbackArguments(Chat chat, Integer messageId, String[] arguments) {
    }

    @Override
    protected SendPhoto startInlineMessageWithPhoto(long chatId) {
        return null;
    }
}
