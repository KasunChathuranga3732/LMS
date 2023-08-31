package lk.ijse.dep10.issueservice.repository;

import lk.ijse.dep10.issueservice.entity.Issue;
import lk.ijse.dep10.issueservice.entity.util.Returned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {

    List<Issue> findIssueByIdLikeOrIsbnLikeOrMemberIdLike(String q1, String q2, String q3);

    List<Issue> findIssueByMemberIdAndReturned(String memberId, Returned returned);

    int countByIsbnAndReturned(String isbn, Returned returned);
}
