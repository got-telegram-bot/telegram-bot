package so.siva.telegram.bot.got_t_bot.telegram.bot.commands.common.info;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.AMarkUppedCommand;

import java.util.Arrays;


public abstract class AInfoCommand extends AMarkUppedCommand {

    protected final boolean withPhoto;

    public AInfoCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController) {
        super(commandIdentifier, description, gotBotListenerController);
        this.withPhoto = false;
    }

    public AInfoCommand(String commandIdentifier, String description, GotBotListenerController gotBotListenerController, boolean withPhoto) {
        super(commandIdentifier, description, gotBotListenerController);
        this.withPhoto = withPhoto;
    }

    @Override
    public void execute(Chat chat, String[] arguments) {
        //Если попали сюда первый раз, то подготавливаем сообщение
        if (arguments.length == 0){
            if (withPhoto){
                execute(startInlineMessageWithPhoto(chat.getId()));
            }else {
                execute(startInlineMessage(chat.getId()));
            }
            return;
        }
        //Параметр messageId добавляет главный контроллер при парсинге пришедшего колбека при нажатии на кнопку
        Integer messageId = Integer.valueOf(arguments[arguments.length - 1]);

        //Если нажали кнопку назад из следующего по вложенности меню, попадаем сюда
        //Формируем сообщение аналогично первому попаданию в команду
        if (BACK_BUTTON_CALLBACK.equals(arguments[0])){
            processUpperLevelBackButtonCallback(chat, messageId, arguments);
            return;
        }

        if (Arrays.asList(arguments).contains(CLOSE_BUTTON_CALLBACK)){
            cancelInfoMessage(chat, messageId);
            return;
        }

        processCallbackArguments(chat, messageId, arguments);
    }

    @Override
    protected String setExitButtonLabel() {
        return DEFAULT_EXIT_BUTTON_LABEL;
    }

    protected abstract void processUpperLevelBackButtonCallback(Chat chat, Integer messageId, String[] arguments);

    protected abstract void processCallbackArguments(Chat chat, Integer messageId, String[] arguments);

    //Один из этих методов должен возвращать null
    protected abstract SendPhoto startInlineMessageWithPhoto(long chatId);
    protected abstract SendMessage startInlineMessage(long chatId);
}
