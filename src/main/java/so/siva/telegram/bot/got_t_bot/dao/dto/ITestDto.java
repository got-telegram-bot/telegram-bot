package so.siva.telegram.bot.got_t_bot.dao.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = TestDto.class)
public interface ITestDto {

    String getTestField();

    void setTestField(String testField);
}
