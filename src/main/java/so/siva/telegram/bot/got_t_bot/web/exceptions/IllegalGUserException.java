package so.siva.telegram.bot.got_t_bot.web.exceptions;

public class IllegalGUserException extends IllegalArgumentException {

    public IllegalGUserException() {
        super();
    }

    public IllegalGUserException(String s) {
        super(s);
    }

    public IllegalGUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalGUserException(Throwable cause) {
        super(cause);
    }
}
