package com.konew.backend.model;

import com.konew.backend.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class BookModel
{
    private String title;

    private String author;

    private CategoryEnum category;

    private Long user_id;
}
