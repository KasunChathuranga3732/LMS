package lk.ijse.dep10.issueservice.entity;

import lk.ijse.dep10.issueservice.util.Flag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Flag returned;
}
