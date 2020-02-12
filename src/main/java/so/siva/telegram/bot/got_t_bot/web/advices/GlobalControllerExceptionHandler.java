package so.siva.telegram.bot.got_t_bot.web.advices;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final String DEFAULT_ERROR = "UnknownException";

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public VndErrors illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new VndErrors("error", ex.getMessage() == null ? DEFAULT_ERROR :  ex.getMessage());
    }
}
