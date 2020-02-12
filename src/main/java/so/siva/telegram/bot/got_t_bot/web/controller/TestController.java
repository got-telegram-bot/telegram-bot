package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.ITestDto;
import so.siva.telegram.bot.got_t_bot.service.ITestService;
import so.siva.telegram.bot.got_t_bot.web.controller.api.ITestController;

@RestController
@RequestMapping("/test")
public class TestController implements ITestController {

    @Autowired
    ITestService testService;

    @Override
    @GetMapping("/get_test")
    public ITestDto testControllerMethod(){
        return testService.getTestDto();
    }
}
