package lk.ijse.dep10.library.book.service.impl;

import lk.ijse.dep10.library.book.dto.BookDTO;
import lk.ijse.dep10.library.book.entity.Book;
import lk.ijse.dep10.library.book.repository.BookRepository;
import lk.ijse.dep10.library.book.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper, RestTemplate restTemplate) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public void saveBook(BookDTO book) {
        if (bookRepository.existsById(book.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The isbn: " + book.getIsbn() + " already exist");
        }
        try {
            bookRepository.save(mapper.map(book, Book.class));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }

    @Override
    public void updateBook(BookDTO book) {
        if (!bookRepository.existsById(book.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The isbn: " + book.getIsbn() + " does not exist");
        }

        try {
            bookRepository.save(mapper.map(book, Book.class));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }

    @Override
    public void deleteBook(String isbn) {
        if (!bookRepository.existsById(isbn)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The isbn: " + isbn+ " does not exist");
        }
        /* Todo: Check whether the book has been issued*/
        String result;
        try{
            result = restTemplate.getForObject("http://localhost:8080/api/v1/issues/book/" + isbn, String.class);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
        }
        if (result != null && result.equals("Yes")){
            throw new ResponseStatusException(HttpStatus.CONFLICT, isbn + ": This is already issued book");
        } else {
            try {
                bookRepository.deleteById(isbn);
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Something went wrong");
            }
        }
    }

    @Override
    public BookDTO getBook(String isbn) {
        return bookRepository.findById(isbn)
                .map(book -> mapper.map(book, BookDTO.class))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "The isbn: " + isbn+ " does not exist"));
    }

    @Override
    public List<BookDTO> findBooks(String query) {
        query = "%" + query + "%";
        return bookRepository.findBooksByIsbnLikeOrTitleLikeOrAuthorLike(query, query, query)
                .stream().map(book -> mapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
}
