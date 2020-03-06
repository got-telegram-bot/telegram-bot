package so.siva.telegram.bot.got_t_bot.config;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class TerminateBean {

    @PreDestroy
    public void onDestroy(){
        System.out.println("--------------TEST_DESTROY_BEAN-----------");
    }
}
