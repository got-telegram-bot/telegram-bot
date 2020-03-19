package so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.roled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import so.siva.telegram.bot.got_t_bot.dao.dto.GUser;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;
import so.siva.telegram.bot.got_t_bot.telegram.bot.handlers.api.ISpecifiedMessageHandler;

@Component
public class MessageHandlerFactory {

    @Autowired
    private ISpecifiedMessageHandler adminMessageHandler;

    @Autowired
    private ISpecifiedMessageHandler playerMessageHandler;

    @Autowired
    private ISpecifiedMessageHandler defaultMessageHandler;

    @Autowired
    private IUserService userService;

    public UserHandlerContainer provideHandler(Message message){

        GUser currentUser = userService.getUserByChatId(message.getChat().getId());

        if (currentUser == null){
            return new UserHandlerContainer(defaultMessageHandler, null);
        }
        else {
            if (currentUser.isAdmin()){
                return new UserHandlerContainer(adminMessageHandler, currentUser);
            }
            if (currentUser.getHouse() != null){
                return new UserHandlerContainer(playerMessageHandler, currentUser);
            }
        }
        return new UserHandlerContainer(defaultMessageHandler, currentUser);
    }

    public static class UserHandlerContainer{
        private ISpecifiedMessageHandler handler;
        private GUser user;

        public UserHandlerContainer(ISpecifiedMessageHandler handler, GUser user) {
            this.handler = handler;
            this.user = user;
        }

        public ISpecifiedMessageHandler getHandler() {
            return handler;
        }

        public GUser getUser() {
            return user;
        }
    }
}
