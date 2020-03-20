package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;

import java.util.ArrayList;
import java.util.List;

import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.DEFAULT_AUTO_CLOSABLE_LABEL;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.GeneralMessageBuilder.prepareDeleteMessage;
import static so.siva.telegram.bot.got_t_bot.telegram.bot.builders.InlineMarkupBuilder.*;

/**
 * Для сообщений с инлайн-клавиатурами
 */
public abstract class AMarkUppedCommand extends ACommand {

    private final String EXIT_BUTTON_LABEL;

    public AMarkUppedCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
        this.EXIT_BUTTON_LABEL = setExitButtonLabel();
    }

    protected String setExitButtonLabel(){
        return DEFAULT_EXIT_BUTTON_LABEL;
    }

    protected InlineKeyboardButton createCommandButton(String label, String callBackData){
        return createButton(label, getCommandIdentifier() + "." + callBackData);
    }

    protected InlineKeyboardButton createCommandBackButton(String callBackData){
        return createCommandButton(DEFAULT_RETURN_BUTTON_LABEL, (StringUtils.isEmpty(callBackData) ? BACK_BUTTON_CALLBACK : callBackData + "." + BACK_BUTTON_CALLBACK));
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
            add(createCommandButton(EXIT_BUTTON_LABEL, CLOSE_BUTTON_CALLBACK));
        }};
    }


}
