package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления системой (только для админа)
 */
@RestController
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    private IAdminService service;

    @GetMapping("/execute_user_ddl")
    public List<IUser> executeUserDdlScript(@RequestParam String fileName){
        return service.executeUserDdl(fileName);
    }

    @PostMapping("/upload_ddl")
    public List<IUser> uploadDdl(@RequestParam("ddl_file")MultipartFile file){
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
