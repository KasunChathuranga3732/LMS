package lk.ijse.dep10.library.book.service.impl;

import lk.ijse.dep10.library.book.dto.BookDTO;
import lk.ijse.dep10.library.book.entity.Book;
import lk.ijse.dep10.library.book.repository.BookRepository;
import lk.ijse.dep10.library.book.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveBook(BookDTO book) {
        if (bookRepository.existsById(book.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The isbn: " + book.getIsbn() + " already exist");
        }
        bookRepository.save(mapper.map(book, Book.class));
    }

    @Override
    public void updateBook(BookDTO book) {
        if (!bookRepository.existsById(book.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The isbn: " + book.getIsbn() + " does not exist");
        }
        bookRepository.save(mapper.map(book, Book.class));
    }

    @Override
    public void deleteBook(String isbn) {
        if (!bookRepository.existsById(isbn)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The isbn: " + isbn+ " does not exist");
        }
        /* Todo: Check whether the book has been issued*/
        bookRepository.deleteById(isbn);
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
