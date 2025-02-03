package in.athenaeum.springbootintegrationtestsdemo.services;

import in.athenaeum.springbootintegrationtestsdemo.exceptions.RecordNotFoundException;
import in.athenaeum.springbootintegrationtestsdemo.models.Book;
import in.athenaeum.springbootintegrationtestsdemo.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImplementation implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImplementation.class);
    private final BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(int bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RecordNotFoundException("Book not found for id " + bookId));
    }

    @Override
    public Book create(Book book) {
        bookRepository.saveAndFlush(book);
        return book;
    }

    @Override
    public Book update(int bookId, Book book) {
        //  get the book from db
        Book bookDb = getById(bookId);
        
        //  copy the new fields to the db book
        BeanUtils.copyProperties(book, bookDb, "bookId");
        
        //  save the db book
        bookRepository.saveAndFlush(bookDb);
        
        //  return the db book
        return bookDb;
    }

    @Override
    public void deleteById(int bookId) {
        //  find the book
        Book bookDb = getById(bookId);
        
        //  delete
        bookRepository.delete(bookDb);
    }
}
