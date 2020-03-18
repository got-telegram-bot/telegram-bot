package so.siva.telegram.bot.got_t_bot.telegram.bot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import so.siva.telegram.bot.got_t_bot.telegram.bot.commands.ACommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class SendMessageAdvice {

    @Autowired
    private List<ACommand> commands;

    @Around(value = "execution(* so.siva.telegram.bot.got_t_bot.telegram.bot.producers.ReplyMarkupProducer.createButton(..)) && args(text, callBackData)")
    public Object addCommandDomainToButtonCallbackData(ProceedingJoinPoint joinPoint, String text, String callBackData) throws Throwable {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        List<String> callbackList = new ArrayList<>();
        commands.forEach(aCommand -> {
            Arrays.stream(stackTrace).forEach(stackTraceElement -> {
                if (aCommand.getClass().getName().equals(stackTraceElement.getClassName())){
                    callbackList.add(aCommand.getCommandIdentifier());
                }
            });
        });

        List<String> distinctCallbackList = callbackList.stream().distinct().collect(Collectors.toList());

        if (distinctCallbackList.size() == 1){
            return joinPoint.proceed(new Object[]{text, distinctCallbackList.get(0) + "." + callBackData});
        }
        if (distinctCallbackList.size() > 1){
            throw new RuntimeException("Button callback advice overflow, бухой мудила, все хуйня, давай сначала");
        }
        return joinPoint.proceed(new Object[]{text, callBackData});
    }
}
