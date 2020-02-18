package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;
import so.siva.telegram.bot.got_t_bot.service.UserService;


import java.util.Arrays;

@Component
public class AuthorizeCommand extends AbstractCommand{


    @Autowired
    private UserService userService;

    public AuthorizeCommand() {
        super("authorize", "авторизация\n");
    }

    @Override
    public void execute(AbsSender absSender, User telegramUser, Chat chat, String[] strings) {

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId());

        Arrays.stream(strings).forEach(System.out::println);

        IUser innerUser = new so.siva.telegram.bot.got_t_bot.dao.dto.User();
        try {
            innerUser.setLogin(strings[0]);
            innerUser.setPassword(strings[1]);

            IUser foundInnerUser = userService.getUserByLoginAndPassword(innerUser);

            if (foundInnerUser == null){
                message.setText("Пользователь не найден");
            }else {
                userService.authorizeUser(foundInnerUser, chat.getId());

                IUser updatedInnerUser = userService.getUserByLoginAndPassword(foundInnerUser);
                message.setText("Пользователь " + updatedInnerUser.getInitials() + " авторизован\n");
            }
        }catch (Throwable t){
            message.setText("Ошибка обработки команды");
        }

        execute(absSender, message, telegramUser);
    }

    private String getName(String[] strings) {

        if (strings == null || strings.length == 0) {
            return null;
        }

        String name = String.join(" ", strings);
        return name.replaceAll(" ", "").isEmpty() ? null : name;
    }
}
