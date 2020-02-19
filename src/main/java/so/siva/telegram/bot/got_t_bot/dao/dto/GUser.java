package so.siva.telegram.bot.got_t_bot.dao.dto;

import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;

public class GUser implements IGUser {
    private String login;
    private String initials;
    private Long chatId;
    private String password;
    private Houses house;
    private Boolean isAdmin;
    private String roleName;


    public GUser() {
    }

    public GUser(IGUser anotherGUser) {
        this.login = anotherGUser.getLogin();
        this.initials = anotherGUser.getInitials();
        this.chatId = anotherGUser.getChatId();
        this.password = anotherGUser.getPassword();
        this.house = anotherGUser.getHouse();
        this.isAdmin = anotherGUser.getAdmin();
        this.roleName = anotherGUser.getRoleName();
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getInitials() {
        return initials;
    }

    @Override
    public void setInitials(String initials) {
        this.initials = initials;
    }

    @Override
    public Long getChatId() {
        return chatId;
    }

    @Override
    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Houses getHouse() {
        return house;
    }

    @Override
    public void setHouse(Houses house) {
        this.house = house;
    }

    @Override
    public Boolean getAdmin() {
        return isAdmin;
    }

    @Override
    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
