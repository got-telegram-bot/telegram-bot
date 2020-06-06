package so.siva.telegram.bot.got_t_bot.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import so.siva.telegram.bot.got_t_bot.dao.api.IUserDao;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (dao.existsById(userForSignUp.getLogin())){
            throw new IllegalArgumentException("Такой пользователь уже существует");
        }
        dao.save(userForSignUp);

        return dao.findByLoginAndPassword(userForSignUp.getLogin(), userForSignUp.getPassword());
    }

    @Override
    public List<GUser> getAllUsers(){
        return transformIterable(dao.findAll());
    }

    @Override
    public List<GUser> getAllUsersPageableByOne(int pageNumber){
        Pageable page = PageRequest.of(pageNumber, 1);
        return transformIterable(dao.findAll(page));
    }

    @Override
    public List<GUser> getAllPlayers(){
        return dao.findByHouseIsNotNull();
    }

    @Override
    public List<GUser> getAdmins(){
        return dao.findByIsAdminTrue();
    }

    @Override
    public List<GUser> getUsersForReadyCheck(){
        List<GUser> gUsers = this.getAllUsers();
        gUsers = gUsers.stream().filter(gUser -> gUser.getChatId() != null && (gUser.isAdmin() || (gUser.getHouse() != null))).collect(Collectors.toList());
        return gUsers;
    }

    @Override
    public GUser getUserByChatId(Long chatId){
        validateChatId(chatId);
        GUser gUser = dao.findByChatId(chatId);
        return gUser;
    }

    @Override
    public GUser authorizeUser(GUser user, Long chatId){
        validate(user);
        validateChatId(chatId);

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

    private List<GUser> transformIterable(Iterable<GUser> users){
        List<GUser> userList = new ArrayList<>();
        users.forEach(userList::add);
        return userList;
    }

    private void validateChatId(Long chatId){
        if (chatId == null){
            throw new IllegalArgumentException("Не передан id чата");
        }
    }
}
