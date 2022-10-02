package easyrecipe.web.easyrecipeWEB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;


@Configuration
@ComponentScan("easyrecipe.web.easyrecipeWEB")
public class SpringConfig implements WebMvcConfigurer {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("postgres://jiahintwtnaiie:59b7911402c6a0f69a520467439af9a07ed23f0910451b2440de20be3ea1419a@ec2-54-155-110-181.eu-west-1.compute.amazonaws.com:5432/ddqrpa8c00m01f");
        dataSource.setUsername("jiahintwtnaiie");
        dataSource.setPassword("59b7911402c6a0f69a520467439af9a07ed23f0910451b2440de20be3ea1419a");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }


}
