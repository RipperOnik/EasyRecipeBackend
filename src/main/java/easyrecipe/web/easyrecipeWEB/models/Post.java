package easyrecipe.web.easyrecipeWEB.models;

import java.util.ArrayList;

public class Post {
    private String id;
    private String urlImage;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private String author;
    private double averageRating;
    private int totalReviewCount;
    private int preparationTimeSeconds;
    private ArrayList<String> ingridients;
    private ArrayList<String> steps;
    private int calories;

    private ArrayList<String> categories;

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public Post() {
    }

    public Post(String id, String urlImage, String title, String author, double averageRating, int totalReviewCount, int preparationTimeSeconds, ArrayList<String> ingridients, ArrayList<String> steps, int calories, ArrayList<String> category) {
        this.id = id;
        this.urlImage = urlImage;
        this.title = title;
        this.author = author;
        this.averageRating = averageRating;
        this.totalReviewCount = totalReviewCount;
        this.preparationTimeSeconds = preparationTimeSeconds;
        this.ingridients = ingridients;
        this.steps = steps;
        this.calories = calories;
        this.categories = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void setTotalReviewCount(int totalReviewCount) {
        this.totalReviewCount = totalReviewCount;
    }

    public void setPreparationTimeSeconds(int preparationTimeSeconds) {
        this.preparationTimeSeconds = preparationTimeSeconds;
    }

    public void setIngridients(ArrayList<String> ingridients) {
        this.ingridients = ingridients;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getId() {
        return id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public String getAuthor() {
        return author;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getTotalReviewCount() {
        return totalReviewCount;
    }

    public int getPreparationTimeSeconds() {
        return preparationTimeSeconds;
    }

    public ArrayList<String> getIngridients() {
        return ingridients;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public int getCalories() {
        return calories;
    }
}
