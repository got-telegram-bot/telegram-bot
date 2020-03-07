package so.siva.telegram.bot.got_t_bot.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import so.siva.telegram.bot.got_t_bot.dao.api.BasicDao;
import so.siva.telegram.bot.got_t_bot.dao.api.IAdminPostMessageDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AdminPostMessageDao extends BasicDao implements IAdminPostMessageDao {

    public AdminPostMessageDao(@Value("${database.schema.messages}") String schemaName, @Value("${database.tablename.current_post}")String tableName) {
        super(schemaName, tableName);
    }

    private String SELECT_ALL_BY_ADMIN_LOGIN = "SELECT * FROM " + SCHEMA_TABLE + " WHERE admin_login = '%s' ORDER BY number_in_post ASC;";
    private String DELETE_ALL_BY_ADMIN_LOGIN = "DELETE FROM " + SCHEMA_TABLE + " WHERE admin_login = '%s';";
    private String INSERT_MESSAGE = "INSERT INTO " + SCHEMA_TABLE + "(" +
            " number_in_post, type, content, file_id, admin_login" +
//            ", media_group_id" +
            ") " +
            " VALUES (?, ?, ?, ?, ?" +
//            ", ?" +
            ") RETURNING *;";

    @Override
    public List<AdminPostMessage> readAllMessagesByAdmin(String adminLogin){
        return jdbcTemplate.query(String.format(SELECT_ALL_BY_ADMIN_LOGIN, adminLogin), (resultSet,i) -> mapAdminPostMessage(resultSet));

    }

    @Override
    public void insertNewMessage(AdminPostMessage message){
        List<Object> params = new ArrayList<>();
        params.add(message.getNumberInPost());
        params.add(message.getAdminPostMessageType().name());
        params.add(message.getContent());
        params.add(message.getFileId());
        params.add(message.getAdminLogin());
//        params.add(message.getMediaGroupId());

        jdbcTemplate.query(INSERT_MESSAGE, params.toArray(), resultSet -> {resultSet.next(); return mapAdminPostMessage(resultSet);});
    }

    @Override
    public void deleteAllMessagesByAdmin(String adminLogin){
        jdbcTemplate.execute(String.format(DELETE_ALL_BY_ADMIN_LOGIN, adminLogin));
    }


    private AdminPostMessage mapAdminPostMessage(ResultSet rs){
        AdminPostMessage message = new AdminPostMessage();
        try {
            message.setNumberInPost(rs.getInt(AdminPostMessage.NUMBER_IN_POST));
            message.setAdminPostMessageType(rs.getObject(AdminPostMessage.TYPE) == null ? null : AdminPostMessageType.valueOf(rs.getString(AdminPostMessage.TYPE).trim()));
            message.setContent(rs.getString(AdminPostMessage.CONTENT));
            message.setFileId(rs.getString(AdminPostMessage.FILE_ID));
            message.setAdminLogin(rs.getString(AdminPostMessage.ADMIN_LOGIN));
//            message.setMediaGroupId(rs.getString(AdminPostMessage.MEDIA_GROUP_ID));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return message;

    }


}
