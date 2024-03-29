package so.siva.telegram.bot.got_t_bot.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public abstract class BasicDao {

    protected JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    protected final String schemaName;
    protected final String tableName;

    protected final String SCHEMA_TABLE;

    public BasicDao(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.SCHEMA_TABLE = schemaName + "." + tableName;

    }

    @Autowired
    protected void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
