package so.siva.telegram.bot.got_t_bot.dao.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;

import javax.persistence.*;

/**
 * Сущность пользователя системы, G - от GoT - от Game of thrones
 */
@JsonDeserialize
@Entity
@Table(name = "users", schema = "users")
public class GUser {

    @Id
    private String login;
    private String initials;
    @Column(name = "chat_id")
    private Long chatId;
    private String password;
    private Houses house;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Column(name = "role_name")
    private String roleName;


    public GUser() {
    }

    public GUser(GUser anotherGUser) {
        this.login = anotherGUser.getLogin();
        this.initials = anotherGUser.getInitials();
        this.chatId = anotherGUser.getChatId();
        this.password = anotherGUser.getPassword();
        this.house = anotherGUser.getHouse();
        this.isAdmin = anotherGUser.getAdmin();
        this.roleName = anotherGUser.getRoleName();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Houses getHouse() {
        return house;
    }

    public void setHouse(Houses house) {
        this.house = house;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
