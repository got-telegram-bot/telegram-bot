package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import so.siva.telegram.bot.got_t_bot.dao.dto.ITestDto;

public interface ITestController {
    @GetMapping("/get_test")
    ITestDto testControllerMethod();
}
