package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Component
public class InfoCommand extends ACommand {


    public InfoCommand(GotBotListenerController gotBotListenerController) {
        super("info", "инфо со ссылками на документы ", gotBotListenerController, false);
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {
        SendMessage message = prepareInlineKeyBoardMessage(chat.getId());

        execute(absSender, message, telegramUser);
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

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Ссылки: ").setReplyMarkup(inlineKeyboardMarkup);
    }
}
