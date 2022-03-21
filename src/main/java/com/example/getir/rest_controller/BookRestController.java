package com.example.getir.rest_controller;

import com.example.getir.controller.BookController;
import com.example.getir.controller.CustomerController;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Book;
import com.example.getir.model.ResponseError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BookRestController {

    private static final Logger logger = LogManager.getLogger(BookRestController.class);

    @Autowired
    BookController bookController;

    @Autowired
    CustomerController customerController;

    @PostConstruct
    public void fillTables() {
        Book book1 = new Book();
        book1.setName("Smurfs");
        book1.setPrice(50.0);
        book1.setStock(5);
        try {
            bookController.addBook(book1);
        } catch (GetirLogicException cce) {
            logger.error(cce.getErrorCode() + ":" + cce.getMessage());
        }

    }

    @GetMapping("/books")
    @ResponseBody
    public ResponseEntity<List<Book>> getBooks(@RequestHeader Map<String, String> headers) {
        try {
            //Validate Token
            customerController.tokenValidation(headers);

            List<Book> bookList = bookController.showAllBooks();

            if (bookList.isEmpty()) {
                logger.info("There are no book in Db");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/books/stock/{bookId}/{stock}")
    @ResponseBody
    public ResponseEntity<Integer> updateBookStock(@RequestHeader Map<String, String> headers, @PathVariable Integer bookId, @PathVariable Integer stock) {
        try {
            //Validate Token
            customerController.tokenValidation(headers);
            Integer success = bookController.updateBookStock(bookId, stock);
            return new ResponseEntity<>(success, HttpStatus.OK);
        } catch (GetirLogicException cce) {
            return new ResponseEntity(new ResponseError(cce.getErrorCode().getCode(), cce.getErrorCode().getDescription()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseError(0, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
