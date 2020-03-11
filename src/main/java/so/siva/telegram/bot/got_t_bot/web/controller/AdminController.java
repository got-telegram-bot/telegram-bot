package so.siva.telegram.bot.got_t_bot.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import so.siva.telegram.bot.got_t_bot.config.CronAwakeConfig;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import java.util.Date;
import java.util.List;

/**
 * Контроллер для управления системой (только для админа)
 */
@RestController
@RequestMapping("/admin")
public class AdminController{

    private Logger logger = LoggerFactory.getLogger(CronAwakeConfig.class);

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AbsSender absSender;

    @GetMapping("/execute_user_ddl")
    public List<GUser> executeUserDdlScript(@RequestParam String fileName){
        return adminService.executeUserDdl(fileName);
    }

    @PostMapping("/upload_ddl")
    public List<GUser> uploadDdl(@RequestParam("ddl_file")MultipartFile file){
        if (file != null) {
            try {
                return adminService.uploadDdl(file.getInputStream());
            } catch (IllegalArgumentException a){
                throw a;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @GetMapping("/ping")
    public String awakeUrl(){
        GUser adminToNotify = userService.getAdmins().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Администратор не найден"));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(adminToNotify.getLogin() + ": ping " + new Date().toString());
        sendMessage.setChatId("381855899");
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error(e.getMessage());
        }
        logger.info("-----------Ping " + new Date().toString());

        return adminToNotify.getLogin();
    }

}
