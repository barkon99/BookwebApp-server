package com.konew.backend.repository;

import com.konew.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "SELECT * from book where is_accepted=FALSE", nativeQuery = true)
    List<Book> getBooksNotAccepted();

    @Query(value = "SELECT * from book where is_accepted=True", nativeQuery = true)
    List<Book> getAcceptedBooks();

    @Query(value = "SELECT * from book where appuser_id=:appuser_id", nativeQuery = true)
    List<Book> getUserBooks(@Param("appuser_id") long appuser_id);

}

