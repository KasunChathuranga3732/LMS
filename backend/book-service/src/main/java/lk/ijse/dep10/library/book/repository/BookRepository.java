package lk.ijse.dep10.library.book.repository;

import lk.ijse.dep10.library.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findBooksByIsbnLikeOrTitleLikeOrAuthorLike(String q1, String q2, String q3);

}
