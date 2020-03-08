package so.siva.telegram.bot.got_t_bot.dao.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @Enumerated(EnumType.STRING)
    private Houses house;
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Column(name = "is_ready")
    private boolean isReady;
    @Column(name = "last_order_message")
    private String lastOrderMessage;

    public GUser() {
    }

    public GUser(GUser anotherGUser) {
        this.login = anotherGUser.getLogin();
        this.initials = anotherGUser.getInitials();
        this.chatId = anotherGUser.getChatId();
        this.password = anotherGUser.getPassword();
        this.house = anotherGUser.getHouse();
        this.isAdmin = anotherGUser.isAdmin();
        this.isReady = anotherGUser.isReady();
        this.lastOrderMessage = anotherGUser.getLastOrderMessage();
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

    @JsonProperty("is_admin")
    public boolean isAdmin() {
        return isAdmin;
    }

    @JsonProperty("is_admin")
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @JsonProperty("is_ready")
    public boolean isReady() {
        return isReady;
    }

    @JsonProperty("is_ready")
    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getLastOrderMessage() {
        return lastOrderMessage;
    }

    public void setLastOrderMessage(String lastOrderMessage) {
        this.lastOrderMessage = lastOrderMessage;
    }
}
