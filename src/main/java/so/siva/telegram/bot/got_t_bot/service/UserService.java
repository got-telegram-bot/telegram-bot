package so.siva.telegram.bot.got_t_bot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.dao.api.IUserDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;

import java.util.List;

@Service
public class UserService implements so.siva.telegram.bot.got_t_bot.service.api.IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public GUser getUserByLoginAndPassword(GUser user){
        validate(user);


        return dao.readUserByLoginAndPassword(user.getLogin(), user.getPassword());
    }

    @Override
    public GUser signUpUser(GUser userForSignUp){
        validate(userForSignUp);
        if (StringUtils.isEmpty(userForSignUp.getInitials())){
            throw new IllegalArgumentException("Не заполнены инициалы");
        }
        dao.insertNewUser(userForSignUp);

        return dao.readUserByLoginAndPassword(userForSignUp.getLogin(), userForSignUp.getPassword());
    }

    @Override
    public List<GUser> getAllUsers(){
        return dao.selectAllUsers();
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
        return dao.updateUser(user);
    }

    @Override
    public List<GUser> deleteUser(String login){
        if (StringUtils.isEmpty(login))
            throw new IllegalArgumentException("Не передан логин");

        dao.deleteUserByLogin(login);
        return this.getAllUsers();
    }

    private void validate(GUser user){
        if (StringUtils.isEmpty(user.getLogin()) || StringUtils.isEmpty(user.getPassword())){
            throw new IllegalArgumentException("Не заполнен логин или пароль");
        }
    }
}
