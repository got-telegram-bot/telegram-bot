package so.siva.telegram.bot.got_t_bot.dao.dto;


import so.siva.telegram.bot.got_t_bot.dao.dto.api.IAdminPostMessage;
import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;

public class AdminPostMessage implements IAdminPostMessage {

    private Integer numberInPost;
    private AdminPostMessageType adminPostMessageType;
    private String content;
    private String fileId;
    private String adminLogin;
//    private String mediaGroupId;

    @Override
    public Integer getNumberInPost() {
        return numberInPost;
    }

    @Override
    public void setNumberInPost(Integer numberInPost) {
        this.numberInPost = numberInPost;
    }

    @Override
    public AdminPostMessageType getAdminPostMessageType() {
        return adminPostMessageType;
    }

    @Override
    public void setAdminPostMessageType(AdminPostMessageType adminPostMessageType) {
        this.adminPostMessageType = adminPostMessageType;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getFileId() {
        return fileId;
    }

    @Override
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String getAdminLogin() {
        return adminLogin;
    }

    @Override
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
