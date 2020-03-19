package so.siva.telegram.bot.got_t_bot.telegram.bot.producers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(scopeName = "prototype")
public class InlineMarkupBuilder {

    public final static String AUTO_CLOSABLE_CALLBACK = "AUTO_CLOSABLE_CALLBACK";

    private final List<List<InlineKeyboardButton>> inlineKeyboard;

    public InlineMarkupBuilder() {
        inlineKeyboard = new ArrayList<>();
    }

    public InlineMarkupBuilder addRow(){
        this.inlineKeyboard.add(new ArrayList<>());
        return this;
    }

    public InlineMarkupBuilder addButton(String label, String callback){
        return addButton(createButton(label, callback));
    }

    public InlineMarkupBuilder addButton(InlineKeyboardButton button){
        if (inlineKeyboard.size() == 0){
            addRow();
        }
        inlineKeyboard.get(inlineKeyboard.size() - 1).add(button);
        return this;
    }

    public InlineKeyboardMarkup buildInlineMarkup(){
        return new InlineKeyboardMarkup().setKeyboard(this.inlineKeyboard);
    }

    public static InlineKeyboardButton createButton(String label, String callback){
        return new InlineKeyboardButton().setText(label).setCallbackData(callback);
    }

    public static InlineKeyboardButton createAutoClosableButton(String label){
        return createButton(label, AUTO_CLOSABLE_CALLBACK);
    }

    public static InlineKeyboardMarkup createAutoClosableMarkup(String label){
        List<List<InlineKeyboardButton>> markup = new ArrayList<>();
        List<InlineKeyboardButton> keyboard = new ArrayList<>();
        keyboard.add(createAutoClosableButton(label));
        markup.add(keyboard);
        return new InlineKeyboardMarkup().setKeyboard(markup);
    }


}
