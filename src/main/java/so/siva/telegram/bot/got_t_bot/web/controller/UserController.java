package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IGUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;


    @PostMapping("/sign_in")
    public IGUser getUserByLoginAndPassword(@RequestBody IGUser user){
        return service.getUserByLoginAndPassword(user);
    }

    @PostMapping("/sign_up")
    public IGUser signUpUser(@RequestBody IGUser user){
        return service.signUpUser(user);
    }

    @GetMapping("/get_all")
    public List<IGUser> getAll(){
        return service.getAllUsers();
    }

    @PostMapping("/authorize")
    public IGUser authorizeUser(@RequestBody IGUser user, @RequestParam Long chatId){
        return service.authorizeUser(user, chatId);
    }

    @PostMapping("/update")
    public IGUser updateUser(@RequestBody IGUser user){
        return service.updateUser(user);
    }

    @DeleteMapping("/delete")
    public List<IGUser> deleteUser(@RequestParam String login){
        return service.deleteUser(login);
    }

}
