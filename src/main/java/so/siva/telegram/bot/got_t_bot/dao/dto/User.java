package so.siva.telegram.bot.got_t_bot.dao.dto;

import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;

public class User implements so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser {
    private String login;
    private String initials;
    private Long chatId;
    private String password;
    private Houses house;
    private Boolean isAdmin;
    private String roleName;

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
