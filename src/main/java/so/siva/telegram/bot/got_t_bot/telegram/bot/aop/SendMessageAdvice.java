package so.siva.telegram.bot.got_t_bot.telegram.bot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SendMessageAdvice {


    @Around(value = "execution(* so.siva.telegram.bot.got_t_bot.telegram.bot.producers.GeneralResponseProducer.prepareSendMessage(..)) && args(message, chatId)")
    public Object captureSendMessage(ProceedingJoinPoint joinPoint, String message, Long chatId) throws Throwable {

        return joinPoint.proceed(new Object[]{"captured", chatId});
    }
}
