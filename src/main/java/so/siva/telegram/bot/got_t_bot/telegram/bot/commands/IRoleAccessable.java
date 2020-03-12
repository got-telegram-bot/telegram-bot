package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

public interface IRoleAccessable {

    void validateAccess(Long chatId);
}
