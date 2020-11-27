package com.konew.backend.service;

import com.konew.backend.entity.*;
import com.konew.backend.exception.ResourceNotFoundException;
import com.konew.backend.model.BookModel;
import com.konew.backend.model.response.BookResponse;
import com.konew.backend.repository.BookRepository;
import com.konew.backend.repository.UserRepository;
import com.konew.backend.security.AuthExceptionHandler;
import com.konew.backend.security.userDetails.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository, RateService rateService, ImageUploader imageUploader) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.rateService = rateService;
        this.imageUploader = imageUploader;
    }

    public List<BookResponse> getBooks()
    {

        return mapToBookResponse(bookRepository.findAll());
    }
    public List<BookResponse> getAcceptedBooks(){
        return mapToBookResponse(bookRepository.getAcceptedBooks());
    }

    public List<BookResponse> mapToBookResponse(List<Book> books){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
            Long currentUserId = principal.getId();

            List<BookResponse> bookResponses = books.stream()
                    .map(book -> new BookResponse(
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getCategory().name(),
                            rateService.getAverageRate(book),
                            book.getRatings()
                                    .stream()
                                    .filter(rate -> rate.getUser().getId() == currentUserId)
                                    .map(value -> value.getValue())
                                    .findFirst()
                                    .orElse(-1),
                            book.getUser().getUsername(),
                            rateService.ratingRepository.getAmountOFRatings(book.getId()),
                            book.getImageUrl()
                    ))
                    .collect(Collectors.toList());
            return bookResponses.stream().map(bookResponse -> ResponseEntity.ok(bookResponse).getBody()).collect(Collectors.toList());
        }
        return null;
    }

    public BookResponse getBookResponse(Long id)
    {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + "does not exist"));
        return mapToBookResponse(Arrays.asList(book)).stream().findFirst()
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
        return mapToBookResponse(bookRepository.getBooksNotAccepted());
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
            return mapToBookResponse(userBooks);
        }
    }
}
