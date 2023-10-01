import { Component } from '@angular/core';
import {Issue} from "../../dto/issue";
import {DatePipe} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {Book} from "../../dto/book";

@Component({
  selector: 'app-issue',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.scss']
})
export class IssueComponent {
  issueList: Array<Issue> = [];
  API_BASE_URL: string = 'http://localhost:8080/api/v1/issues';
  return: string = 'All';
  id =  null;
  isbn: string = '';
  memberId: string = '';
  pipe = new DatePipe('en-US');
  issueDate = this.pipe.transform(new Date(), 'yyyy-MM-dd');
  dueDate = this.pipe.transform(new Date().setDate(new Date().getDate() + 10), 'yyyy-MM-dd');

  constructor(private http:HttpClient, private toastr: ToastrService) {
    http.get<Array<Issue>>(`${this.API_BASE_URL}`)
      .subscribe(issueList => {
        this.issueList = issueList
        console.log(issueList);
      }, (err) => {
        this.toastr.error("Can't fetch issues: " + err.statusText, 'Error');
      });
  }

}
