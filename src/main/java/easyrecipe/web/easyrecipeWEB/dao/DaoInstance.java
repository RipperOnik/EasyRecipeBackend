package easyrecipe.web.easyrecipeWEB.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DaoInstance {

    private static DriverManagerDataSource dataSource = new DriverManagerDataSource();



    public static Dao getDAO(){
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/EasyRecipe");
        dataSource.setUsername("postgres");
        dataSource.setPassword("42248019");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Dao dao = new Dao(jdbcTemplate);
        return dao;
    }
}
