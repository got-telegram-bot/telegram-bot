package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.List;

/**
 * Для сообщений с инлайн-клавиатурами
 */
public abstract class AMarkUppedCommand extends ACommand {

    private final String EXIT_BUTTON_LABEL;
    public final String DEFAULT_EXIT_BUTTON_LABEL = "✖ Выход ✖";
    private final static String RETURN_BUTTON_LABEL = "⬅ Назад";

    protected final static String CLOSE_BUTTON_CALLBACK = "CANCEL";
    protected final static String BACK_BUTTON_CALLBACK = "BACK";

    public AMarkUppedCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
        this.EXIT_BUTTON_LABEL = setExitButtonLabel();
    }

    protected abstract String setExitButtonLabel();

    /**
     * Закрыть инфо, удалив сообщение
     */
    protected void cancelInfoMessage(Chat chat, Integer messageId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chat.getId());
        deleteMessage.setMessageId(messageId);
        execute(deleteMessage);
    }

    protected InlineKeyboardButton createButton(String text, String callBackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(getCommandIdentifier() + "." + callBackData);

        return button;
    }

    protected EditMessageMedia prepareEditMessagePhoto(
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

    protected EditMessageText prepareEditMessageText(
            List<List<InlineKeyboardButton>> commonRowList,
            String newMessage,
            InlineKeyboardButton backButton,
            Long chatId, Integer messageId
    ){
        EditMessageText editMessageText = new EditMessageText();
        List<List<InlineKeyboardButton>> completeRowList = new ArrayList<>(commonRowList);
        completeRowList.add(prepareNavigateButtonRow(backButton));
        editMessageText.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(completeRowList));
        editMessageText.setText(newMessage);
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        return editMessageText;
    }


    protected InlineKeyboardButton createBackButton(String callBackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(RETURN_BUTTON_LABEL);
        button.setCallbackData(getCommandIdentifier() + "." + (StringUtils.isEmpty(callBackData) ? BACK_BUTTON_CALLBACK : callBackData + "." + BACK_BUTTON_CALLBACK));

        return button;
    }

    protected List<InlineKeyboardButton> prepareNavigateButtonRow(InlineKeyboardButton backButton){
        return new ArrayList<InlineKeyboardButton>(prepareNavigateButtonRow()){{
            if (backButton != null){
                add(backButton);
            }
        }};
    }

    protected List<InlineKeyboardButton> prepareNavigateButtonRow(){
        return new ArrayList<InlineKeyboardButton>(){{
            add(createButton(EXIT_BUTTON_LABEL, CLOSE_BUTTON_CALLBACK));
        }};
    }


}
