package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import so.siva.telegram.bot.got_t_bot.essences.users.GUser;
import so.siva.telegram.bot.got_t_bot.essences.users.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService service;


    @PostMapping("/sign_in")
    public GUser getUserByLoginAndPassword(@RequestBody GUser user){
        return service.getUserByLoginAndPassword(user);
    }

    @PostMapping("/sign_up")
    public GUser signUpUser(@RequestBody GUser user){
        return service.signUpUser(user);
    }

    @GetMapping("/get_all")
    public List<GUser> getAll(){
        return service.getAllUsers();
    }

    @GetMapping("/get_page")
    public List<GUser> getAll(@RequestParam(name = "page") Integer pageNumber ){
        return service.getAllUsersPageableByOne(pageNumber);
    }

    @PostMapping("/authorize")
    public GUser authorizeUser(@RequestBody GUser user, @RequestParam Long chatId){
        return service.authorizeUser(user, chatId);
    }

    @PostMapping("/update")
    public GUser updateUser(@RequestBody GUser user){
        return service.updateUser(user);
    }

    @DeleteMapping("/delete")
    public List<GUser> deleteUser(@RequestParam String login){
        return service.deleteUser(login);
    }

}
