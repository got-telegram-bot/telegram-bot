package so.siva.telegram.bot.got_t_bot.dao.dto;


import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;

public class AdminPostMessage {
    public static final String NUMBER_IN_POST = "number_in_post";
    public static final String TYPE = "type";
    public static final String CONTENT = "content";
    public static final String FILE_ID = "file_id";
    public static final String ADMIN_LOGIN = "admin_login";

    private Integer numberInPost;
    private AdminPostMessageType adminPostMessageType;
    private String content;
    private String fileId;
    private String adminLogin;
//    private String mediaGroupId;

    public Integer getNumberInPost() {
        return numberInPost;
    }

    public void setNumberInPost(Integer numberInPost) {
        this.numberInPost = numberInPost;
    }

    public AdminPostMessageType getAdminPostMessageType() {
        return adminPostMessageType;
    }

    public void setAdminPostMessageType(AdminPostMessageType adminPostMessageType) {
        this.adminPostMessageType = adminPostMessageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

//    @Override
//    public String getMediaGroupId() {
//        return mediaGroupId;
//    }
//
//    @Override
//    public void setMediaGroupId(String mediaGroupId) {
//        this.mediaGroupId = mediaGroupId;
//    }
}
