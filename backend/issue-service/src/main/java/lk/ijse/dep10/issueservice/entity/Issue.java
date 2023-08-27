package lk.ijse.dep10.issueservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    private int id;
    @Column(nullable = false)
    private String isbn;
    @Column(name = "member-id", nullable = false)
    private String memberId;
    @Column(name = "issue-date", nullable = false)
    private Date issueDate;
    @Column(name = "return-date", nullable = false)
    private Date returnDate;
    @Column(nullable = false)
    private double fine;
    @Column(nullable = false)
    private String returned;
}
