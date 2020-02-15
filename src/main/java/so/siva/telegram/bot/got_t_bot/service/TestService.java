package so.siva.telegram.bot.got_t_bot.service;

import org.springframework.stereotype.Service;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.ITestDto;
import so.siva.telegram.bot.got_t_bot.dao.dto.TestDto;

@Service
public class TestService implements ITestService {

    @Override
    public ITestDto getTestDto(){
        ITestDto testDto = new TestDto();
        testDto.setTestField("Валар Моргулис");
        return testDto;
    }
}
