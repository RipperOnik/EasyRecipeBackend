package easyrecipe.web.easyrecipeWEB.dao;

import easyrecipe.web.easyrecipeWEB.models.Post;
import org.jibx.schema.codegen.extend.DefaultNameConverter;
import org.jibx.schema.codegen.extend.NameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

//@Component
public class Dao {
    private final JdbcTemplate jdbcTemplate;

    private static final String URL = "jdbc:postgresql://ec2-54-155-110-181.eu-west-1.compute.amazonaws.com:5432/ddqrpa8c00m01f";
    private static final String USERNAME = "jiahintwtnaiie";
    private static final String PASSWORD = "59b7911402c6a0f69a520467439af9a07ed23f0910451b2440de20be3ea1419a";
    public static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


//    @Autowired
    public Dao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Post> searchPosts(String query) throws SQLException {
        query = query.toLowerCase();
        String[] words = query.split("_");
        String queryLowerCasePlural = "%";
        String queryLowerCaseSingular = "%";
        String queryUpperCasePlural = "%";
        String queryUpperCaseSingular = "%";
        NameConverter nameTools = new DefaultNameConverter();


        for (int i = 0; i < words.length; i++){
            queryLowerCaseSingular += nameTools.depluralize(words[i]) + "%";
            queryLowerCasePlural += nameTools.pluralize(words[i]) + "%";

            String capitalizedWord = words[i].substring(0,1).toUpperCase() + words[i].substring(1);
            queryUpperCaseSingular += nameTools.depluralize(capitalizedWord) + "%";
            queryUpperCasePlural += nameTools.pluralize(capitalizedWord) + "%";
        }

        PreparedStatement preparedStatement = connection.prepareStatement("select * from post where title like ? or title like ? or title like ? or title like ? limit 24");
        preparedStatement.setString(1, queryUpperCaseSingular);
        preparedStatement.setString(2, queryUpperCasePlural);
        preparedStatement.setString(3, queryLowerCaseSingular);
        preparedStatement.setString(4, queryLowerCasePlural);

        ResultSet resultSet1 = preparedStatement.executeQuery();

        ArrayList<Post> posts = new ArrayList<>();
        while (resultSet1.next()){
            getPosts(resultSet1, posts);

        }
        return posts;
    }

    private void getPosts(ResultSet resultSet1, ArrayList<Post> posts) throws SQLException {
        Post post = new Post();
        post.setId(resultSet1.getString("id"));
        post.setUrlImage(resultSet1.getString("url_image"));
        post.setTitle(resultSet1.getString("title"));
        post.setAuthor(resultSet1.getString("author"));
        post.setAverageRating(resultSet1.getDouble("average_rating"));
        post.setTotalReviewCount(resultSet1.getInt("total_review_count"));
        post.setPreparationTimeSeconds(resultSet1.getInt("preparation_time_seconds"));
        post.setCalories(resultSet1.getInt("calories"));


        PreparedStatement preparedStatement2 = connection.prepareStatement("select ingridient_id from post_ingridient where post_id = ?");
        preparedStatement2.setString(1,resultSet1.getString("id"));
        ResultSet resultSet2 = preparedStatement2.executeQuery();
        ArrayList<String> ingridients = new ArrayList<>();
        while (resultSet2.next()){
            ingridients.add(resultSet2.getString("ingridient_id"));
        }

        PreparedStatement preparedStatement3 = connection.prepareStatement("select step_id from post_step where post_id = ?");
        preparedStatement3.setString(1,resultSet1.getString("id"));
        ResultSet resultSet3 = preparedStatement3.executeQuery();
        ArrayList<String> steps = new ArrayList<>();
        while (resultSet3.next()){
            steps.add(resultSet3.getString("step_id"));
        }

        PreparedStatement preparedStatement4 = connection.prepareStatement("select category_id from post_category where post_id = ?");
        preparedStatement4.setString(1,resultSet1.getString("id"));
        ResultSet resultSet4 = preparedStatement4.executeQuery();
        ArrayList<String> categories = new ArrayList<>();
        while (resultSet4.next()){
            categories.add(resultSet4.getString("category_id"));
        }


        post.setIngridients(ingridients);
        post.setSteps(steps);
        post.setCategories(categories);

        posts.add(post);
    }

    //tested
    public ArrayList<Post> getPostsByCategory(String category) throws SQLException {


        PreparedStatement preparedStatement = connection.prepareStatement("select post_id from post_category where category_id=?");
        preparedStatement.setString(1,category);

        ArrayList<String> ids = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            ids.add(resultSet.getString("post_id"));
        }

        ArrayList<Post> posts = new ArrayList();

        ids.forEach(id -> {

            try {
                PreparedStatement preparedStatement1 = connection.prepareStatement("select * from post where id = ?");
                preparedStatement1.setString(1, id);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                resultSet1.next();


                getPosts(resultSet1, posts);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return posts;




    }
    //tested
    public void createPost(Post post){
        jdbcTemplate.update("insert into post values (?,?,?,?,?,?,?,?) on conflict (id) do nothing", post.getId(), post.getUrlImage(), post.getTitle(), post.getAuthor(), post.getAverageRating(), post.getTotalReviewCount(), post.getPreparationTimeSeconds(), post.getCalories());
        for (int i=0; i < post.getIngridients().size(); i++){
            jdbcTemplate.update("insert into ingridient values (?) on conflict (whole_line) do nothing", post.getIngridients().get(i));
            jdbcTemplate.update("insert into post_ingridient (post_id, ingridient_id) values(?,?) on conflict (post_id,ingridient_id) do nothing", post.getId(), post.getIngridients().get(i));
        }
        if (post.getSteps() != null){
            for (int i=0; i < post.getSteps().size(); i++){
                jdbcTemplate.update("insert into step values (?) on conflict (step_line) do nothing", post.getSteps().get(i));
                jdbcTemplate.update("insert into post_step (post_id, step_id) values(?,?) on conflict (post_id,step_id) do nothing", post.getId(), post.getSteps().get(i));
            }
        }

        for (int i = 0; i < post.getCategories().size(); i++){
            jdbcTemplate.update("insert into category values (?) on conflict (category_name) do nothing", post.getCategories().get(i));
            jdbcTemplate.update("insert into post_category (post_id, category_id) values(?,?) on conflict (post_id,category_id) do nothing", post.getId(), post.getCategories().get(i));
        }
    }

//    //tested
//    public ArrayList<UserLikedPost> getUserLikesPosts(String login) throws SQLException {
//        ArrayList<UserLikedPost> userLikedPosts = new ArrayList<>();
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from account_post where account_id = ?");
//        preparedStatement.setString(1, login);
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()){
//            UserLikedPost userLikedPost = new UserLikedPost(login, resultSet.getString("post_id"));
//            userLikedPosts.add(userLikedPost);
//        }
//        return userLikedPosts;
//    }
//
//    //tested
//    public void addUserLikedPost(String login, String postId){
//        jdbcTemplate.update("insert into account_post values(?,?) on conflict (account_id, post_id) do nothing ", login, postId);
//    }
//    //tested
//    public void deleteUserLikedPost(String login, String postId){
//        jdbcTemplate.update("delete from account_post where account_id=? and post_id=?", login, postId);
//    }


    //tested
//    public Account getAccount(String login){
//        return jdbcTemplate.query("select * from account where login=?", new BeanPropertyRowMapper<>(Account.class),login)
//                .stream()
//                .findAny()
//                .orElse(null);
//    }
//
//
//
//
//    //tested
//    public boolean register(String login, String password){
//        if(getAccount(login) != null){
//            return false;
//        }
//        else{
//            jdbcTemplate.update("insert into account values (?,?)", login, password);
//            return true;
//        }
//    }
//    //tested
//    public boolean login(String login, String password){
//        if(jdbcTemplate.query("select * from account where login=? and password=?", new BeanPropertyRowMapper<>(Account.class),login, password)
//                .stream()
//                .findAny()
//                .orElse(null) != null){
//            return true;
//        }
//        else{
//            return false;
//        }
//
//
//    }



}
