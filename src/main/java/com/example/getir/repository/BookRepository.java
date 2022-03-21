package com.example.getir.repository;

import com.example.getir.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;

import javax.transaction.Transactional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Modifying
    @Query("UPDATE Book SET stock = :stock WHERE id = :bookId")
    Integer updateBookStock(Integer stock, Integer bookId);
}
