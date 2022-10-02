package easyrecipe.web.easyrecipeWEB;

import com.google.gson.Gson;
import easyrecipe.web.easyrecipeWEB.dao.Dao;
import easyrecipe.web.easyrecipeWEB.dao.DaoInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class Controller {

    private final Dao dao;

    @Autowired
    public Controller(Dao dao) {
        this.dao = dao;
    }
//    public Dao dao = DaoInstance.getDAO();




    @GetMapping("/api/posts/{category}")
    public ResponseEntity<String> getPostsByCategory(@PathVariable("category") String category) throws SQLException {
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String postsJSON = new Gson().toJson(dao.getPostsByCategory(category));

        return new ResponseEntity(postsJSON, httpHeaders, HttpStatus.OK);

    }
    @GetMapping("/api/posts/search/{query}")
    public ResponseEntity<String> searchPosts(@PathVariable("query") String query) throws SQLException {
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String postsJSON = new Gson().toJson(dao.searchPosts(query));

        return new ResponseEntity(postsJSON, httpHeaders, HttpStatus.OK);
    }

}
