package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.info;

import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.List;

public abstract class AInfoCommand extends ACommand {

    protected final static String CLOSE_BUTTON_CALLBACK = "CANCEL";
    protected final static String BACK_BUTTON_CALLBACK = "BACK";


    public AInfoCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
    }

    public AInfoCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController, boolean isAdminCommand) {
        super(commandIdentifier, description, gotBotListenerController, isAdminCommand);
    }

    /**
     * Закрыть инфо, удалив сообщение
     */
    protected void cancelInfoWindow(AbsSender absSender, User telegramUser, Chat chat, Integer messageId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chat.getId());
        deleteMessage.setMessageId(messageId);
        execute(absSender, deleteMessage, telegramUser);
    }

    protected InlineKeyboardButton createButton(String text, String callBackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(getCommandIdentifier() + "." + callBackData);

        return button;
    }

    protected InlineKeyboardButton createBackButton(String callBackData){
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("« Назад");
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
            add(createButton("⊗ Выход ⊗", CLOSE_BUTTON_CALLBACK));
        }};
    }

}
