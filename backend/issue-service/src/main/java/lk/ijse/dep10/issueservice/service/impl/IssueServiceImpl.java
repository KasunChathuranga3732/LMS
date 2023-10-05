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
import java.util.stream.Collectors;

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
    public IssueDTO saveIssue(IssueDTO issue) {
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

        if(book != null) {
            int totalBooks = book.getCopies();
            int issueCount = issueRepository.countByIsbnAndReturned(book.getIsbn(), Returned.NO);

            if (totalBooks <= issueCount) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        issue.getIsbn() + ": This book not available. All copies have been released.");
            }
        }
        try {
            return mapper.map(issueRepository.save(mapper.map(issue, Issue.class)), IssueDTO.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }

    @Override
    public void updateIssue(IssueDTO issue) {
        if(!issueRepository.existsById(issue.getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The issue: " + issue.getId() + " does not exist");
        }

        try{
            issueRepository.save(mapper.map(issue, Issue.class));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }

    @Override
    public void deleteIssue(Integer id) {
        if(!issueRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The issue: " + id + " does not exist");
        }

        try{
            issueRepository.deleteById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }


    }

    @Override
    public List<IssueDTO> findIssues(String query) {
        List<Issue> issueList;

        try {
            Integer id = Integer.parseInt(query);
            issueList = issueRepository.findIssuesByIdLike(id);
        } catch (NumberFormatException e){
            query = "%" + query + "%";
            issueList = issueRepository.findIssueByIsbnLikeOrMemberIdLike(query, query);
        }

        return issueList.stream().map(issue -> mapper.map(issue, IssueDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getIssueByIsbn(String isbn) {
        try {
            int count = issueRepository.countByIsbnAndReturned(isbn, Returned.NO);
            if (count > 0) {
                return "Yes";
            } else {
                return "No";
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }

    @Override
    public String getIssueByMemberId(String id) {
        try {
            int count = issueRepository.findIssueByMemberIdAndReturned(id, Returned.NO).size();
            if (count > 0) {
                return "Yes";
            } else {
                return "No";
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }

    @Override
    public Integer getCountNonReturnsCopies(String isbn) {
        try {
            return issueRepository.countByIsbnAndReturned(isbn, Returned.NO);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Something went wrong");
        }
    }
}
