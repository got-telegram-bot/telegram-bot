package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.essences.admin.IAdminService;

import java.util.List;

/**
 * Контроллер для управления системой (только для админа)
 */
@RestController
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    private IAdminService service;

    @GetMapping("/execute_user_ddl")
    public List<GUser> executeUserDdlScript(@RequestParam String fileName){
        return service.executeUserDdl(fileName);
    }

    @PostMapping("/upload_ddl")
    public List<GUser> uploadDdl(@RequestParam("ddl_file")MultipartFile file){
        if (file != null) {
            try {
                return service.uploadDdl(file.getInputStream());
            } catch (IllegalArgumentException a){
                throw a;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }

    }

}
