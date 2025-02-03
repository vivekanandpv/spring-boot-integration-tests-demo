package in.athenaeum.springbootintegrationtestsdemo.apis;

import in.athenaeum.springbootintegrationtestsdemo.models.Book;
import in.athenaeum.springbootintegrationtestsdemo.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
@Tag(name = "Book API", description = "RESTful Web API for book resource")
public class BookApi {
    
    private final BookService bookService;


    public BookApi(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }
    
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping(value = "{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        return ResponseEntity.ok(bookService.getById(bookId));
    }
    
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.create(book));
    }
    
    @PutMapping("{bookId}")
    public ResponseEntity<Book> updateBookPut(@PathVariable int bookId, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.update(bookId, book));
    }

    @PatchMapping("{bookId}")
    public ResponseEntity<Book> updateBookPatch(@PathVariable int bookId, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.update(bookId, book));
    }
    
    @DeleteMapping("{bookId}")
    public ResponseEntity<Book> deleteBook(@PathVariable int bookId) {
        bookService.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }
}
