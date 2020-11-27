package com.konew.backend.service;

import com.konew.backend.entity.*;
import com.konew.backend.exception.ResourceNotFoundException;
import com.konew.backend.model.BookRateModel;
import com.konew.backend.repository.BookRepository;
import com.konew.backend.repository.RatingRepository;
import com.konew.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    RatingRepository ratingRepository;
    UserRepository userRepository;
    BookRepository bookRepository;

    @Autowired
    public RateService(RatingRepository ratingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public double getAverageRate(Book book)
    {
        double avg_rate = book.getRatings().stream()
                .mapToInt(x -> x.getValue())
                .average()
                .orElse(0d);
        return Math.round(avg_rate*10)/10.0;
    }
    public Rating getUserRates(Long userId, Long bookId)
    {
        return ratingRepository.getUserRates(userId,bookId).orElseThrow(() -> new RuntimeException("No ratings for this user"));

    }

    public void saveOrUpdateRate(BookRateModel bookRateModel)
    {
        Book book = bookRepository.findById(bookRateModel.getBookId()).orElseThrow(() ->
                new ResourceNotFoundException("Book with id " + bookRateModel.getBookId() + "does not exist")
        );

        User user = userRepository.findById(bookRateModel.getUserId());
        if (user == null)
            throw new ResourceNotFoundException("User with id " + bookRateModel.getUserId() + "does not exist");


        Rating rating =  new Rating();
        rating.setValue(bookRateModel.getRate());
        rating.setUser(user);
        rating.setBook(book);

        if(checkRates(user.getId(),book.getId()) == false) {

            ratingRepository.save(rating);
        }

        else {
            Rating updateRating = getUserRates(user.getId(), book.getId());
            updateRating.setValue(bookRateModel.getRate());
            ratingRepository.save(updateRating);
        }
    }

    public boolean checkRates(long userId, long bookId)
    {
        return ratingRepository.getUserRates(userId,bookId).isPresent();
    }
}
