package so.siva.telegram.bot.got_t_bot.dao.dto;


import so.siva.telegram.bot.got_t_bot.dao.emuns.AdminPostMessageType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(schema = "messages", name = "current_post")
@IdClass(AdminPostMessage.NumberAndLoginPrimaryKey.class)
public class AdminPostMessage {

    @Id
    @Column(name = "number_in_post")
    private Integer numberInPost;
    @Column(name = "type")
    private AdminPostMessageType adminPostMessageType;
    @Column(name = "content")
    private String content;
    @Column(name = "file_id")
    private String fileId;
    @Id
    @Column(name = "admin_login")
    private String adminLogin;

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



    public static class NumberAndLoginPrimaryKey implements Serializable {
        static final long serialVersionUID = 1L;

        private Integer numberInPost;
        private String adminLogin;

        public NumberAndLoginPrimaryKey() {
        }

        public NumberAndLoginPrimaryKey(Integer numberInPost, String adminLogin) {
            this.numberInPost = numberInPost;
            this.adminLogin = adminLogin;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage.NumberAndLoginPrimaryKey that = (so.siva.telegram.bot.got_t_bot.dao.dto.AdminPostMessage.NumberAndLoginPrimaryKey) o;
            return Objects.equals(numberInPost, that.numberInPost) &&
                    Objects.equals(adminLogin, that.adminLogin);
        }

        @Override
        public int hashCode() {
            return Objects.hash(numberInPost, adminLogin);
        }

        @Override
        public String toString() {
            return "NumberAndLoginPrimaryKey{" +
                    "numberInPost='" + numberInPost + '\'' +
                    ", adminLogin='" + adminLogin + '\'' +
                    '}';
        }
    }
}
