package lk.ijse.dep10.issueservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private int copies;
}
