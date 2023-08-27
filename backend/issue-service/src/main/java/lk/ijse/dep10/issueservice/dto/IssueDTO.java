package lk.ijse.dep10.issueservice.dto;

import lk.ijse.dep10.issueservice.util.Flag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueDTO implements Serializable {
    @NotBlank(message = "Id can't be empty")
    private int id;
    @NotBlank(message = "ISBN can't be empty")
    private String isbn;
    @NotBlank(message = "Member Id can't be empty")
    private String memberId;
    @NotBlank(message = "Issue date can't be empty")
    private Date issueDate;
    @NotBlank(message = "Return date can't be empty")
    private Date returnDate;
    @NotBlank(message = "Fine can't be empty")
    private double fine;
    @NotBlank(message = "Returned should be Yes or No")
    private Flag returned;
}
