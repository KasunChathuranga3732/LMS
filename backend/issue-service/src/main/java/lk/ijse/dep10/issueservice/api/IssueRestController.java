package lk.ijse.dep10.issueservice.api;

import lk.ijse.dep10.issueservice.dto.IssueDTO;
import lk.ijse.dep10.issueservice.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/issues")
@CrossOrigin
public class IssueRestController {

    private final IssueService issueService;

    public IssueRestController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveIssue(@RequestBody @Validated IssueDTO issueDTO){
        issueService.saveIssue(issueDTO);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateIssue(@PathVariable String id, @RequestBody @Validated IssueDTO issueDTO){
        issueDTO.setId(Integer.parseInt(id));
        issueService.updateIssue(issueDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssue(@PathVariable String id){
        issueService.deleteIssue(Integer.parseInt(id));
    }

    @GetMapping
    public List<IssueDTO> getIssues(@RequestParam(required = false, name = "q") String query){
        if(query == null) query = "";
        return issueService.findIssues(query);
    }

    @GetMapping("/book/{isbn}")
    public String getIssuesByIsbn(@PathVariable String isbn){
        return issueService.getIssueByIsbn(isbn);
    }

    @GetMapping("/member/{id}")
    public String getIssuesByMember(@PathVariable String id){
        return issueService.getIssueByMemberId(id);
    }
}
