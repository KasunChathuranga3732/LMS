package lk.ijse.dep10.library.book.api;

import lk.ijse.dep10.library.book.dto.BookDTO;
import lk.ijse.dep10.library.book.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@CrossOrigin
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveBook(@RequestBody @Validated BookDTO bookDTO){
        bookService.saveBook(bookDTO);
    }

    @PatchMapping(value = "/{isbn}", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateBook(@PathVariable String isbn, @RequestBody @Validated BookDTO bookDTO){
        bookDTO.setIsbn(isbn);
        bookService.updateBook(bookDTO);
    }

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBook(@PathVariable String isbn){
        bookService.deleteBook(isbn);
    }

    @GetMapping("/{isbn}")
    public BookDTO getBook(@PathVariable String isbn){
        return bookService.getBook(isbn);
    }

    @GetMapping
    public List<BookDTO> findBooks(@RequestParam(required = false) String query){
        if(query == null) query = "";
        return bookService.findBooks(query);
    }
}
