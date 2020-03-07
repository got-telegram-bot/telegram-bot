package so.siva.telegram.bot.got_t_bot.dao;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import so.siva.telegram.bot.got_t_bot.dao.api.BasicDao;
import so.siva.telegram.bot.got_t_bot.dao.api.IUserDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.dao.emuns.Houses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


//@Repository
//@Transactional
public class UserDao extends BasicDao
//        implements IUserDao
{

    public UserDao(@Value("${database.schema.users}") String schemaName, @Value("${database.tablename.users}")String tableName) {
        super(schemaName, tableName);
    }

    private String SELECT_BY_LOGIN_AND_PASSWORD = "SELECT * FROM " + SCHEMA_TABLE + " WHERE login = '%s' AND password = '%s'";

    private String SIGN_UP_NEW_USER = "INSERT INTO " + SCHEMA_TABLE
            + "( login, initials, chat_id, password, house, is_admin, role_name )"
            + "VALUES ('%s', '%s', NULL, '%s', NULL, false, NULL);";

    private String SELECT_ALL_FROM_USERS = "SELECT * FROM " + SCHEMA_TABLE;

    private String DELETE_USER_BY_LOGIN = "DELETE FROM " + SCHEMA_TABLE + " WHERE login = '%s'";

    private String UPDATE_USER = "UPDATE users.users" +
            " SET login=?, initials=?, chat_id=?, password=?, house=?, is_admin=?, role_name=?" +
            " WHERE login = '%s' "
            +" RETURNING * ;";

    private String SELECT_USER_BY_CHAT_ID = "SELECT * FROM " + SCHEMA_TABLE + " WHERE chat_id = '%s'";


//    @Override
    public GUser readUserByLoginAndPassword(String login, String password){
        String selectQuery = String.format(SELECT_BY_LOGIN_AND_PASSWORD, login, password);
        return getFirstUserInList(jdbcTemplate.query(selectQuery, (resultSet, i) -> mapUser(resultSet)));
    }

//    @Override
    public GUser readUserByChatId(String chatId){
        return getFirstUserInList(jdbcTemplate.query(String.format(SELECT_USER_BY_CHAT_ID, chatId), (resultSet, i) -> mapUser(resultSet)));
    }

//    @Override
    public void insertNewUser(GUser user){
        String insertQuery = String.format(SIGN_UP_NEW_USER, user.getLogin(), user.getInitials(), user.getPassword());
        jdbcTemplate.execute(insertQuery);
    }

//    @Override
    public List<GUser> selectAllUsers(){
        return jdbcTemplate.query(SELECT_ALL_FROM_USERS, (resultSet,i) -> mapUser(resultSet));
    }

//    @Override
    public void deleteUserByLogin(String login){
        jdbcTemplate.execute(String.format(DELETE_USER_BY_LOGIN, login));
    }

//    @Override
    public GUser updateUser(GUser user){
        List<Object> params = new ArrayList<>();
        params.add(user.getLogin());
        params.add(user.getInitials());
        params.add(user.getChatId());
        params.add(user.getPassword());
        params.add(user.getHouse());
        params.add(user.getAdmin());
        params.add(user.getRoleName());

        String updateQuery = String.format(UPDATE_USER, user.getLogin());
        return jdbcTemplate.query(updateQuery, params.toArray(), (resultSet -> {
            resultSet.next();
            return mapUser(resultSet);
        }));

    }

    private GUser getFirstUserInList(List<Object> users){
        if (users == null || users.size() == 0){
            return null;
        }else return (GUser) users.get(0);
    }

    private GUser mapUser(ResultSet rs){
        GUser user = new GUser();
        try {
            user.setLogin(rs.getString(GUser.LOGIN));
            user.setInitials(rs.getString(GUser.INITIALS));
            user.setChatId(rs.getObject(GUser.CHAT_ID) == null ? null : rs.getLong(GUser.CHAT_ID));
            user.setPassword(rs.getString(GUser.PASSWORD));
            user.setHouse(rs.getString(GUser.HOUSE) == null ? null : Houses.valueOf(rs.getString(GUser.HOUSE).trim()));
            user.setAdmin(rs.getBoolean(GUser.IS_ADMIN));
            user.setRoleName(rs.getString(GUser.ROLE_NAME) == null ? null : rs.getString(GUser.ROLE_NAME).trim());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return user;
    }
}
