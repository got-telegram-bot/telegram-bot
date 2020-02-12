package so.siva.telegram.bot.got_t_bot.dao.dto.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import so.siva.telegram.bot.got_t_bot.dao.dto.TestDto;

@JsonDeserialize(as = TestDto.class)
public interface ITestDto {

    String getTestField();

    void setTestField(String testField);
}
