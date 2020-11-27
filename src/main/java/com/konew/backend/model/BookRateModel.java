package com.konew.backend.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BookRateModel
{
    @Min(1)
    @Max(10)
    private int rate;
    private long bookId;
    private long userId;

    public BookRateModel() {
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setUserID(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
