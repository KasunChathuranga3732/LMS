package lk.ijse.dep10.issueservice.dto;

import lk.ijse.dep10.issueservice.entity.util.Returned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueDTO implements Serializable {
    private int id;
    @NotBlank(message = "ISBN can't be empty")
    @Pattern(regexp = "\\d{10,13}",message = "Invalid ISBN")
    private String isbn;
    @NotBlank(message = "Member Id can't be empty")
    private String memberId;
    @NotNull(message = "Issue date can't be empty")
    private Date issueDate;
    @NotNull(message = "Return date can't be empty")
    private Date returnDate;
    @PositiveOrZero(message = "Fine can't be negative")
    private double fine;
    @NotNull(message = "Returned should be Yes or No")
    private Returned returned;

    public IssueDTO(String isbn, String memberId, Date issueDate,
                    Date returnDate, double fine, Returned returned) {
        this.isbn = isbn;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.returned = returned;
    }
}
