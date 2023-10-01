package lk.ijse.dep10.issueservice.entity;

import lk.ijse.dep10.issueservice.entity.util.Returned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "return_date", nullable = false)
    private Date returnDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fine;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Returned returned;
}
