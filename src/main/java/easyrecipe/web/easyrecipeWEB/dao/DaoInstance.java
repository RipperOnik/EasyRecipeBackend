package easyrecipe.web.easyrecipeWEB.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DaoInstance {

    private static DriverManagerDataSource dataSource = new DriverManagerDataSource();



    public static Dao getDAO(){
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("postgres://jiahintwtnaiie:59b7911402c6a0f69a520467439af9a07ed23f0910451b2440de20be3ea1419a@ec2-54-155-110-181.eu-west-1.compute.amazonaws.com:5432/ddqrpa8c00m01f");
        dataSource.setUsername("jiahintwtnaiie");
        dataSource.setPassword("59b7911402c6a0f69a520467439af9a07ed23f0910451b2440de20be3ea1419a");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Dao dao = new Dao(jdbcTemplate);
        return dao;
    }
}
