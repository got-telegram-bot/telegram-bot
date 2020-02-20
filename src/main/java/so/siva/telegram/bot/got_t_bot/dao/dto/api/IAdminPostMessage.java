package so.siva.telegram.bot.got_t_bot.dao.dto.api;

import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;

public interface IAdminPostMessage {

    String NUMBER_IN_POST = "number_in_post";
    String TYPE = "type";
    String CONTENT = "content";
    String FILE_ID = "file_id";
    String ADMIN_LOGIN = "admin_login";

    Integer getNumberInPost();

    void setNumberInPost(Integer numberInPost);

    AdminPostMessageType getAdminPostMessageType();

    void setAdminPostMessageType(AdminPostMessageType adminPostMessageType);

    String getContent();

    void setContent(String content);

    String getFileId();

    void setFileId(String fileId);

    String getAdminLogin();

    void setAdminLogin(String adminLogin);
}
