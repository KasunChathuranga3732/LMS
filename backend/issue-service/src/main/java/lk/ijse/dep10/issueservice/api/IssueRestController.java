package lk.ijse.dep10.issueservice.api;

import lk.ijse.dep10.issueservice.dto.IssueDTO;
import lk.ijse.dep10.issueservice.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String saveIssue(@RequestBody @Validated IssueDTO issueDTO){
        issueService.saveIssue(issueDTO);
        return "<h1>Save Issue</h1>";
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateIssue(){
        return "<h1>Update Issue</h1>";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteIssue(){
        return "<h1>Delete Issue</h1>";
    }

    @GetMapping
    public String getIssues(@RequestParam(required = false, name = "q") String query){
        return "<h1>Get Issues</h1>";
    }
}
