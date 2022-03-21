package com.example.getir.dao_impl;

import com.example.getir.dao.BookDao;
import com.example.getir.enums.ErrorCode;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Book;
import com.example.getir.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Transactional
@Service
public class BookDaoImpl implements BookDao {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getBooksAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public List<Book> saveAll(List<Book> bookList) {
        return bookRepository.saveAll(bookList);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Integer updateBookStock(Integer bookId, Integer stock) {
        return bookRepository.updateBookStock(stock, bookId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public  Integer decraseBookStock(Integer bookId, Integer itemCount) throws GetirLogicException {
        try {
            Book book = getBookById(bookId);
            int stock = book.getStock();
            if (stock - itemCount < 0) {
                throw new GetirLogicException(ErrorCode.INVALID_STOCK);
            }
            stock = stock - itemCount;
            Integer updated = updateBookStock(bookId, stock);
            return updated;
        } catch (Exception ce) {
            throw new GetirLogicException(ce.getMessage(), ErrorCode.DATABASE);
        }

    }
}
