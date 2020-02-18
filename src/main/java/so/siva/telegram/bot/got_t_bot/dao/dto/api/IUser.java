package so.siva.telegram.bot.got_t_bot.dao.dto.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import so.siva.telegram.bot.got_t_bot.dao.dto.User;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;

@JsonDeserialize(as = User.class)
public interface IUser {
    String LOGIN = "login";
    String INITIALS = "initials";
    String CHAT_ID = "chat_id";
    String PASSWORD = "password";
    String HOUSE = "house";
    String IS_ADMIN = "is_admin";
    String ROLE_NAME = "role_name";

    String getLogin();

    void setLogin(String login);

    String getInitials();

    void setInitials(String initials);

    Long getChatId();

    void setChatId(Long chatId);

    String getPassword();

    void setPassword(String password);

    Houses getHouse();

    void setHouse(Houses house);

    Boolean getAdmin();

    void setAdmin(Boolean admin);

    String getRoleName();

    void setRoleName(String roleName);

}
