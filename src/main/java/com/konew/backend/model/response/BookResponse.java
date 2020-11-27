package com.konew.backend.model.response;

public class BookResponse
{
    private Long id;
    private String title;
    private String author;
    private String category;
    private double avg_rate;
    private int user_rate;
    private String userName;
    private int amountOfRatings;
    private String imageUrl;

    public BookResponse() {
    }
    public BookResponse(Long id, String title, String author)
    {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public BookResponse(Long id, String title, String author, String category, double avg_rate,
                        int user_rate, String userName, int amountOfRatings, String imageUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.avg_rate = avg_rate;
        this.user_rate = user_rate;
        this.userName = userName;
        this.amountOfRatings = amountOfRatings;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAvg_rate() {
        return avg_rate;
    }

    public void setAvg_rate(double avg_rate) {
        this.avg_rate = avg_rate;
    }

    public int getUser_rate() {
        return user_rate;
    }

    public void setUser_rate(int user_rate) {
        this.user_rate = user_rate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAmountOfRatings() {
        return amountOfRatings;
    }

    public void setAmountOfRatings(int amountOfRatings) {
        this.amountOfRatings = amountOfRatings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
