package com.konew.backend.model;

import com.konew.backend.entity.CategoryEnum;
import org.springframework.web.multipart.MultipartFile;

public class BookModel
{
    private String title;

    private String author;

    private CategoryEnum category;

    private Long user_id;

    public BookModel(String title, String author, CategoryEnum category, Long user_id) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.user_id = user_id;
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

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
