package com.example.getir.controller;

import com.example.getir.dao_impl.BookDaoImpl;
import com.example.getir.enums.ErrorCode;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {
//    Will persist new book
//    Will update book’s stock

    private static final Logger logger = LogManager.getLogger(BookController.class);

    @Autowired
    BookDaoImpl bookDaoImpl;

    public Book addBook(Book book) throws GetirLogicException {
        try {
            Book savedBook = bookDaoImpl.saveBook(book);
            return book;
        } catch (Exception ce) {
            logger.error("Book Could Not Added");
            throw new GetirLogicException(ce.getMessage(), ErrorCode.DATABASE);
        }
    }

    public Integer updateBookStock(Integer bookId, Integer stock) throws GetirLogicException {
        try {
            Integer updatedStock = bookDaoImpl.updateBookStock(bookId, stock);
            return updatedStock;
        } catch (Exception ce) {
            logger.error("Stock Of Book Could Not Updated!!");
            throw new GetirLogicException(ce.getMessage(), ErrorCode.DATABASE);
        }
    }


    public List<Book> showAllBooks() throws GetirLogicException {
        try {
            return bookDaoImpl.getBooksAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    public Book getBookByID(Integer bookId) throws GetirLogicException {
        try {
            return bookDaoImpl.getBookById(bookId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(e.getMessage(), ErrorCode.DATABASE);
        }
    }
}
