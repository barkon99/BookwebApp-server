package com.konew.backend.controller;

import com.konew.backend.entity.Book;
import com.konew.backend.model.response.BookResponse;
import com.konew.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/books")
public class AdminController
{
    BookService bookService;

    @Autowired
    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public List<BookResponse> getBooksToAccept()
    {
        return bookService.getBooksToAccept();
    }

    @GetMapping("/{id}")
    public void acceptBook(@PathVariable long id){
        Book book = bookService.getBook(id);
        book.setAccepted(true);
        bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public void rejectBook(@PathVariable long id){
        bookService.deleteBook(id);
    }
}
