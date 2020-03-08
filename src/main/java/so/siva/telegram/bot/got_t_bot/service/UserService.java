package so.siva.telegram.bot.got_t_bot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.dao.api.IUserDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserDao dao;

    public UserService(IUserDao dao) {
        this.dao = dao;
    }

    @Override
    public GUser getUserByLoginAndPassword(GUser user){
        validate(user);


        return dao.findByLoginAndPassword(user.getLogin(), user.getPassword());
    }

    @Override
    public GUser signUpUser(GUser userForSignUp){
        validate(userForSignUp);
        if (StringUtils.isEmpty(userForSignUp.getInitials())){
            throw new IllegalArgumentException("Не заполнены инициалы");
        }
        dao.save(userForSignUp);

        return dao.findByLoginAndPassword(userForSignUp.getLogin(), userForSignUp.getPassword());
    }

    @Override
    public List<GUser> getAllUsers(){
        Iterable<GUser> users = dao.findAll();
        List<GUser> userList = new ArrayList<>();
        users.forEach(userList::add);
        return userList;
    }

    @Override
    public GUser authorizeUser(GUser user, Long chatId){
        validate(user);
        if (chatId == null){
            throw new IllegalArgumentException("Не передан id чата");
        }
        user.setChatId(chatId);
        return this.updateUser(user);

    }

    @Override
    public GUser updateUser(GUser user){
        validate(user);
        return dao.save(user);
    }

    @Override
    public List<GUser> deleteUser(String login){
        if (StringUtils.isEmpty(login))
            throw new IllegalArgumentException("Не передан логин");

        dao.deleteById(login);
        return this.getAllUsers();
    }

    private void validate(GUser user){
        if (StringUtils.isEmpty(user.getLogin()) || StringUtils.isEmpty(user.getPassword())){
            throw new IllegalArgumentException("Не заполнен логин или пароль");
        }
    }
}
