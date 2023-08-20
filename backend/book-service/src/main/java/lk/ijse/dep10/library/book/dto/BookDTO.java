package lk.ijse.dep10.library.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO implements Serializable {
    @NotBlank(message = "ISBN can't be empty")
    private String isbn;
    @NotBlank(message = "Title can't be empty")
    private String title;
    @NotBlank(message = "Author can't be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid author name")
    private String author;
//    @NotBlank(message = "Copies can't be null")
    @PositiveOrZero(message = "Copies can't be negative")
    private Integer copies;
}
