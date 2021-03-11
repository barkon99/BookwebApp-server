package com.konew.backend.service;

import com.konew.backend.entity.*;
import com.konew.backend.exception.ResourceNotFoundException;
import com.konew.backend.model.BookModel;
import com.konew.backend.model.response.BookResponse;
import com.konew.backend.repository.BookRepository;
import com.konew.backend.repository.RatingRepository;
import com.konew.backend.repository.UserRepository;
import com.konew.backend.security.AuthExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookService
{
    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);
    BookRepository bookRepository;
    UserRepository userRepository;
    RateService rateService;
    ImageUploader imageUploader;
    BookAuthenticationService bookAuthenticationService;
    RatingRepository ratingRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository, RateService rateService, ImageUploader imageUploader, RatingRepository ratingRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.rateService = rateService;
        this.imageUploader = imageUploader;
        this.ratingRepository = ratingRepository;
    }

    @Autowired
    public void setBookAuthenticationService(BookAuthenticationService bookAuthenticationService) {
        this.bookAuthenticationService = bookAuthenticationService;
    }

    public List<BookResponse> getBooks()
    {
        return getBookResponse(bookRepository.findAll());
    }
    public List<BookResponse> getAcceptedBooks(){
        return getBookResponse(bookRepository.getAcceptedBooks());
    }


    public List<BookResponse> getBookResponse(List<Book> books){
//        bookAuthenticationService = new BookAuthenticationService();
        Authentication authentication = bookAuthenticationService.getAuthentication();
        if(bookAuthenticationService.isInstanceUserDetailsImpl(authentication)) {
            Long currentUserId = bookAuthenticationService.getUserId(authentication);

            List<BookResponse> bookResponses = mapBookToBookResponse(books, currentUserId);
            return bookResponses;
        }
        return null;
    }

    private List<BookResponse> mapBookToBookResponse(List<Book> books, Long currentUserId) {
        return books.stream()
                        .map(book -> BookResponse.builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .author(book.getAuthor())
                                .category(book.getCategory().name())
                                .avg_rate(rateService.getAverageRate(book))
                                .user_rate(getUserRate(book,currentUserId))
                                .userName(book.getUser().getUsername())
                                .amountOfRatings(ratingRepository.getAmountOFRatings(book.getId()))
                                .imageUrl(book.getImageUrl())
                                .build()
                        )
                        .collect(Collectors.toList());
    }
    private int getUserRate(Book book, Long currentUserId){
        return book.getRatings()
                .stream()
                .filter(rate -> rate.getUser().getId() == currentUserId)
                .map(value -> value.getValue())
                .findFirst()
                .orElse(-1);
    }

    public BookResponse getBookResponse(Long id)
    {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "does not exist"));
        return getBookResponse(Arrays.asList(book)).stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "does not exist"));
    }

    public Book getBook(Long id)
    {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "does not exist"));
        return book;
    }

    public void save(BookModel bookModel, Optional<MultipartFile> file){
        User user = userRepository.findById(bookModel.getUser_id())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookModel.getUser_id() + "does not exist"));

        Book book = new Book();
        book.setTitle(bookModel.getTitle());
        book.setAuthor(bookModel.getAuthor());
        book.setCategory(bookModel.getCategory());
        book.setUser(user);

        String imageUrl = uploadImage(file);
        book.setImageUrl(imageUrl);

        if(isAdmin(bookModel.getUser_id())) book.setAccepted(true);
        else book.setAccepted(false);

        bookRepository.save(book);

    }
    public Book save(Book book){
        bookRepository.save(book);
        return book;
    }


    public void updateBook(Long id, BookModel updatedBook, Optional<MultipartFile> file, boolean isChange) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book with id " + id + "does not exist")
        );
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setCategory(updatedBook.getCategory());

        if(isChange){
            String imageUrl = uploadImage(file);
            book.setImageUrl(imageUrl);
        }

        bookRepository.save(book);
    }

    public void deleteBook(Long id)
    {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book with id " + id + "does not exist")
        );
        List<Rating> bookRatings = rateService.ratingRepository.findByBook_Id(id);
        bookRatings.forEach(rate ->{
            rateService.ratingRepository.deleteById(rate.getId());
        });

        bookRepository.delete(book);
    }

    public List<BookResponse> getBooksToAccept(){
        return getBookResponse(bookRepository.getBooksNotAccepted());
    }

    public boolean isAdmin(long id){
        User user = userRepository.findById(id);
        return user.getRoles().stream()
                .filter(role -> role.getName().name() == "ROLE_ADMIN")
                .findAny()
                .isPresent();
    }

    public String uploadImage(Optional<MultipartFile> file){
        String imageUrl = null;
        if(file.isPresent()) {
            try {
                imageUrl = imageUploader.uploadFile(file.get());
            } catch (IOException e) {
                logger.error("File exception: ", e.getMessage());
            }
        }
        return imageUrl;
    }

    public List<BookResponse> getUserBooks(Long id)
    {
        List<Book> userBooks = bookRepository.getUserBooks(id);
        if(userBooks.size() == 0) return null;
        else{
            return getBookResponse(userBooks);
        }
    }
}
