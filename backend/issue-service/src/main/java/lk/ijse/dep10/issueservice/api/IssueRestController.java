package lk.ijse.dep10.issueservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
@CrossOrigin
public class IssueRestController {

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveIssue(){
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
