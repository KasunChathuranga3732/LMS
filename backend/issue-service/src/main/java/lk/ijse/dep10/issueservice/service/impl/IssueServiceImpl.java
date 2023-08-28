package lk.ijse.dep10.issueservice.service.impl;

import lk.ijse.dep10.issueservice.dto.IssueDTO;
import lk.ijse.dep10.issueservice.entity.Book;
import lk.ijse.dep10.issueservice.entity.Issue;
import lk.ijse.dep10.issueservice.repository.IssueRepository;
import lk.ijse.dep10.issueservice.service.IssueService;
import lk.ijse.dep10.issueservice.entity.util.Returned;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;

    public IssueServiceImpl(IssueRepository issueRepository, ModelMapper mapper, RestTemplate restTemplate) {
        this.issueRepository = issueRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public void saveIssue(IssueDTO issue) {
        List<Issue> list = issueRepository.findIssueByMemberIdAndReturned(issue.getMemberId(), Returned.NO);
        if(list.size() == 3){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    issue.getMemberId()+" member has already received three books.");
        }

        Book book = null;
        try {
            book = restTemplate.getForObject("http://localhost:8080/api/v1/books/" + issue.getIsbn(), Book.class);

        } catch (HttpClientErrorException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    issue.getIsbn()+": This book not found.");
        }

        if(book != null){
            int totalBooks = book.getCopies();
            int issueCount = issueRepository.countByIsbnAndReturned(book.getIsbn(), Returned.NO);

            if(totalBooks <= issueCount){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        issue.getIsbn()+": This book not available. All copies have been released.");
            }
        }

        try{
            issueRepository.save(mapper.map(issue, Issue.class));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal Server Error");
        }
    }

    @Override
    public void updateIssue(IssueDTO issue) {
        if(issueRepository.existsById(issue.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The issue: " + issue.getId() + " does not exist");
        }

        try{
            issueRepository.save(mapper.map(issue, Issue.class));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Internal Server Error");
        }
    }

    @Override
    public void deleteIssue(IssueDTO issue) {

    }

    @Override
    public List<IssueDTO> findIssues(String query) {
        return null;
    }
}
