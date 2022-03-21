package com.example.getir.dao;



import com.example.getir.model.Book;

import java.util.List;


public interface BookDao {
    List<Book> getBooksAll();

    Book getBookById(Integer id);

    List<Book> saveAll(List<Book> bookList);

    Book saveBook(Book book);

    Book updateBook(Book book);

    Integer updateBookStock(Integer bookId,Integer stock);
}
