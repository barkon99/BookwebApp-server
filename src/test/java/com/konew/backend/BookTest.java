package com.konew.backend;

import com.konew.backend.entity.Book;
import com.konew.backend.model.response.BookResponse;
import com.konew.backend.service.BookService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;


public class BookTest
{
    BookService bookService = mock(BookService.class);

    @Test
    public void getBooks(){
        given(bookService.getBooks()).willReturn(prepareMockData());
        List<BookResponse> books = bookService.getBooks();
        Assert.assertEquals(2, books.size());
    }
    @Test
    public void addBook(){
        Book book = new Book();
        book.setId(1);
        book.setTitle("Pan Tadeusz");
        book.setAuthor("Adam Mickiewicz");

        given(bookService.save(Mockito.any(Book.class))).willReturn(book);

        bookService.save(book);

        Assert.assertEquals("Adam Mickiewicz", book.getAuthor());
    }

    public List<BookResponse> prepareMockData()
    {
        List<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setId(1);
        book.setAuthor("Henryk Sienkiewicz");
        book.setTitle("Krzyzacy");

        Book book2 = new Book();
        book2.setId(1);
        book2.setAuthor("Stansilaw Wyspianski");
        book2.setTitle("Wesele");

        books.add(book);
        books.add(book2);

        List<BookResponse> bookResponses = books.stream()
                .map(b -> BookResponse.builder()
                        .id(b.getId())
                        .title(b.getTitle())
                        .author(b.getAuthor())
                        .build())
                .collect(Collectors.toList());
        return bookResponses;
    }
}
