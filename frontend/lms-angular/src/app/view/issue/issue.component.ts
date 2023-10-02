import { Component } from '@angular/core';
import {Issue} from "../../dto/issue";
import {DatePipe} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {Book} from "../../dto/book";
import * as $ from "jquery";
import {Member} from "../../dto/member";

@Component({
  selector: 'app-issue',
  templateUrl: './issue.component.html',
  styleUrls: ['./issue.component.scss']
})
export class IssueComponent {
  issueList: Array<Issue> = [];
  API_BASE_URL_ISSUE: string = 'http://localhost:8080/api/v1/issues';
  API_BASE_URL_BOOK: string = 'http://localhost:8080/api/v1/books';
  API_BASE_URL_MEMBER: string = 'http://localhost:8080/api/v1/members';
  return: string = 'All';
  id =  null;
  isbn: string = '';
  memberId: string = '';

  constructor(private http:HttpClient, private toastr: ToastrService) {
    http.get<Array<Issue>>(`${this.API_BASE_URL_ISSUE}`)
      .subscribe(issueList => {
        this.issueList = issueList
      }, (err) => {
        this.toastr.error("Can't fetch issues: " + err.statusText, 'Error');
      });
  }

  getIssues(txtSearch: HTMLInputElement) {
    const searchText = txtSearch.value.trim();
    const query = (searchText) ? `?q=${searchText}`: "";
    this.http.get<Array<Issue>>(`${this.API_BASE_URL_ISSUE}` + query)
      .subscribe(issueList => {
        if(this.return === 'All'){
          this.issueList = issueList
        } else {
          this.issueList = issueList.filter(issue => issue.returned === 'NO');
        }
      }, (err) => {
        this.toastr.error("Can't fetch issues: "+err.statusText, 'Error');
      });
  }

  newIssue(txtIsbn: HTMLInputElement, txtMemberId: HTMLInputElement) {
    $('new-issue-modal').trigger;
    [txtIsbn, txtMemberId].forEach(txt => {
      txt.classList.remove('is-invalid', 'animate__shakeX');
      txt.value = '';
    })
    setTimeout(()=>txtIsbn.focus(),500);
  }

  issueBook(txtIsbn: HTMLInputElement, txtMemberId: HTMLInputElement) {
    if(!this.validData(txtIsbn, txtMemberId)){
      return
    }

  }


  private validData(txtIsbn: HTMLInputElement, txtMemberId: HTMLInputElement) {
    let valid = true;

    [txtIsbn, txtMemberId].forEach(txt => {
      txt.classList.remove('is-invalid', 'animate__shakeX');
    })

    if(!this.isbn){
      valid = this.invalidate(txtIsbn, "ISBN can't be empty");
    } else {
      this.http.get<Book>(`${this.API_BASE_URL_BOOK}/` + this.isbn)
        .subscribe(book => {
          const book1 = book;
        }, (err) => {
          valid = this.invalidate(txtIsbn, this.isbn + " :This book doesn't exist");
        });
    }

    if(!this.memberId){
      valid = this.invalidate(txtMemberId, "Member id can't be empty");
    } else {
      this.http.get<Member>(`${this.API_BASE_URL_MEMBER}/` + this.memberId)
        .subscribe(member => {
          const member1 = member;
        }, (err) => {
          valid = this.invalidate(txtMemberId, this.memberId + " :This member doesn't exist");
        });
    }
    return valid;
  }

  private invalidate(txt: HTMLInputElement, msg: string) {
    setTimeout(()=>txt.classList.add('is-invalid', 'animate__shakeX'),0);
    txt.select();
    $(txt).next().text(msg);
    return false;
  }
}
