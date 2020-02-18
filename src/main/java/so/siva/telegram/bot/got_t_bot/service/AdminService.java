package so.siva.telegram.bot.got_t_bot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;
import so.siva.telegram.bot.got_t_bot.dao.dto.api.IUser;
import so.siva.telegram.bot.got_t_bot.service.api.IAdminService;
import so.siva.telegram.bot.got_t_bot.service.api.IUserService;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class AdminService implements IAdminService {


    @Autowired
    private IUserService userService;

    private DataSource dataSource;

    @Autowired
    protected void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<IUser> executeUserDdl(String fileName){
        String packagePath = this.getClass().
                getProtectionDomain().
                getCodeSource().
                getLocation().
                getPath().
                replaceFirst("/", "").
                replaceFirst("%20", " ").
                replaceFirst("target/classes/","target/");


        try {
            File sqlFile = new File(packagePath + fileName);
            executeSqlFile(sqlFile.getPath());

        }catch (Throwable throwable){
            System.out.println(throwable.getMessage());
            throw new RuntimeException("Ошибка доступа к файлу");
        }



        return userService.getAllUsers();
    }

    private void executeSqlFile(String filePath){
        StringBuilder sqlBuilder = new StringBuilder();

        try {

            List<String> lines = Files.readAllLines(
                    Paths.get(filePath), StandardCharsets.UTF_8);

            for(String line: lines){
                //Файл записывается в одну строку, поэтому комментарии необходимо удалить,
                // иначе интерпретатор будет считать, что все содержимое файла закомментированно.
                if (!line.startsWith("--")){
                    sqlBuilder.append(line);
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            executeSqlScript(dataSource.getConnection(), new StringBuffer(sqlBuilder));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void executeSqlScript(Connection connection, StringBuffer sql)throws SQLException {
        try {
            connection.setAutoCommit(false);
            ScriptUtils.executeSqlScript(connection, new ByteArrayResource(sql.toString().getBytes()));
            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection.rollback();
        }finally{
            connection.close();
        }
    }

}
