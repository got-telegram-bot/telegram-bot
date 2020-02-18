package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;


    @PostMapping("/sign_in")
    public IUser getUserByLoginAndPassword(@RequestBody IUser user){
        return service.getUserByLoginAndPassword(user);
    }

    @PostMapping("/sign_up")
    public IUser signUpUser(@RequestBody IUser user){
        return service.signUpUser(user);
    }

    @GetMapping("/get_all")
    public List<IUser> getAll(){
        return service.getAllUsers();
    }

    @PostMapping("/authorize")
    public IUser authorizeUser(@RequestBody IUser user, @RequestParam Long chatId){
        return service.authorizeUser(user, chatId);
    }

    @PostMapping("/update")
    public IUser updateUser(@RequestBody IUser user){
        return service.updateUser(user);
    }

    @DeleteMapping("/delete")
    public List<IUser> deleteUser(@RequestParam String login){
        return service.deleteUser(login);
    }

}
