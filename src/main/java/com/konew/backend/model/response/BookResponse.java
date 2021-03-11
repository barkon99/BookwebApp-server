package com.konew.backend.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
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


}
