package so.siva.telegram.bot.got_t_bot.dao.dto;


public class TestDto implements ITestDto {

    private String testField;

    @Override
    public String getTestField() {
        return testField;
    }

    @Override
    public void setTestField(String testField) {
        this.testField = testField;
    }
}
