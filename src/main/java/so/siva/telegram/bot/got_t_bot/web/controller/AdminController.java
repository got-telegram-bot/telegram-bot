package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminService;

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
    public List<IUser> executeUserDdlScript(@RequestParam String fileName){
        return service.executeUserDdl(fileName);
    }

}
