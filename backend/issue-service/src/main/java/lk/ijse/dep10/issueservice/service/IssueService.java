package lk.ijse.dep10.issueservice.service;

import lk.ijse.dep10.issueservice.dto.IssueDTO;

import java.util.List;

public interface IssueService {

    void saveIssue(IssueDTO issue);

    void updateIssue(IssueDTO issue);

    void deleteIssue(Integer id);

    List<IssueDTO> findIssues(String query);

    String getIssueByIsbn(String isbn);

    String getIssueByMemberId(String id);

}
