package so.siva.telegram.bot.got_t_bot.telegram.bot.commands;

import so.siva.telegram.bot.got_t_bot.essences.users.GUser;

public interface IRoleAccessible {

    GUser validateAccess(Long chatId);
}
