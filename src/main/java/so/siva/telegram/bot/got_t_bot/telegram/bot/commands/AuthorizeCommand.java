package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;
import so.siva.telegram.bot.got_t_bot.service.UserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.GotBotListenerController;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class AuthorizeCommand extends ACommand {


    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AuthorizeCommand.class);

    public AuthorizeCommand(GotBotListenerController gotBotListenerController) {
        super("authorize", "авторизация\n", gotBotListenerController);
    }

    /**
     * Ждем в команду переданный через пробел логин и пароль, уже зарегистированного в системе пользователя
     * Получив пользователя из базы по логину и паролю, присваиваем этому пользователю chat_id, с которого он авторизовался.
     * chat_id для авторизованного пользователя должен быть уникальным, поэтому при авторизации из нового чата, происходит
     * автоматический сброс chat_id у предыдущего пользователя.
     *
     * @param absSender
     * @param telegramUser
     * @param chat
     * @param strings
     */
    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {

        SendMessage message = new SendMessage();
        Long currentChatId = chat.getId();
        message.setChatId(currentChatId);

        Arrays.stream(strings).forEach(s -> logger.info("Parameter "  + s));

        try {

            if (strings.length != 2 || StringUtils.isEmpty(strings[0]) || StringUtils.isEmpty(strings[1])){
                message.setText("Передайте логин и пароль через пробел");
            }
            final String currentGUserLogin = strings[0];
            final String currentGUserPassword = strings[1];

            List<IGUser> fullGUserList = userService.getAllUsers();

            IGUser foundGUserByLoginAndPassword = fullGUserList.stream()
                    .filter(igUser ->
                            (igUser.getLogin().equals(currentGUserLogin) && igUser.getPassword().equals(currentGUserPassword))
                    ).findFirst().orElse(null);

            if (foundGUserByLoginAndPassword == null){
                message.setText("Пользователь не найден");

            //Если нашли пользователя в системе, обрабатываем его
            }else {
                String answerMessage;

                //Ищем пользователя с таким же chat_id, если находим, инвалидируем его, и авторизуем текущего
                IGUser foundGUserByChatId = fullGUserList.stream().filter(igUser -> currentChatId.equals(igUser.getChatId())).findFirst().orElse(null);
                if (foundGUserByChatId != null){
                    if (!foundGUserByChatId.getLogin().equals(foundGUserByLoginAndPassword.getLogin())){
                        invalidateUser(absSender, telegramUser, foundGUserByChatId, currentChatId);
                    }
                    else {
                        message.setText("Вы уже авторизованы в системе");
                        execute(absSender, message, telegramUser);
                        return;
                    }
                }
                if (foundGUserByLoginAndPassword.getChatId() != null && !currentChatId.equals(foundGUserByLoginAndPassword.getChatId())){
                    invalidateUser(absSender, telegramUser, foundGUserByLoginAndPassword, foundGUserByLoginAndPassword.getChatId());
                }

                userService.authorizeUser(foundGUserByLoginAndPassword, chat.getId());

                IGUser updatedInnerUser = userService.getUserByLoginAndPassword(foundGUserByLoginAndPassword);
                answerMessage = "Пользователь " + updatedInnerUser.getInitials() + " авторизован\n";
                logger.info(answerMessage + " [" + updatedInnerUser.getLogin() + "]");
                message.setText(answerMessage);

            }
        }catch (Throwable t){
            message.setText("Ошибка обработки команды");
        }

        execute(absSender, message, telegramUser);
    }

    private void invalidateUser(AbsSender absSender, User telegramUser, IGUser igUser,Long chatId){
        IGUser igUserForInvalidate = new GUser(igUser);
        igUserForInvalidate.setChatId(null);

        IGUser invalidatedUser =  userService.updateUser(igUserForInvalidate);

        if (invalidatedUser.getChatId() != null){
            logger.error("User " + invalidatedUser.getLogin() + " haven't been invalidate");
        }
        logger.warn("Invalidate user " + igUser.getLogin());
        SendMessage invalidateMessage = new SendMessage();
        invalidateMessage.setChatId(chatId);
        invalidateMessage.setText("Ваша учетная запись " + invalidatedUser.getLogin() + " была авторизована в другом чате, текущий чат инвалидирован");
        execute(absSender, invalidateMessage, telegramUser);
    }

}
