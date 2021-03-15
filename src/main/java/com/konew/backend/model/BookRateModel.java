package com.konew.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class BookRateModel
{
    @Min(1)
    @Max(10)
    private int rate;
    private long bookId;
    private long userId;
}
