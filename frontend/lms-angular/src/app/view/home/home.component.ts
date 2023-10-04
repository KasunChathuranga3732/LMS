import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {Member} from "../../dto/member";
import {Book} from "../../dto/book";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  totalMembers: string = '';
  totalBooks: string = '';
  totalIssues: string = '';
  totalFines: string = '';
  availableBooks: string = '';
  issuedBooks: string = '';
  API_BASE_URL_ISSUE: string = 'http://localhost:8080/api/v1/issues';
  API_BASE_URL_BOOK: string = 'http://localhost:8080/api/v1/books';
  API_BASE_URL_MEMBER: string = 'http://localhost:8080/api/v1/members';

  constructor(private http:HttpClient, private toastr: ToastrService) {
    this.getMembers()
    this.getBooks()
  }

  getMembers(){
    this.http.get<Array<Member>>(`${this.API_BASE_URL_MEMBER}`)
      .subscribe(memberList => {
        this.totalMembers = '' + memberList.length
      }, (err) => {
        this.toastr.error("Can't fetch members: " + err.statusText, 'Error');
      })
  }

  getBooks(){
    this.http.get<Array<Book>>(`${this.API_BASE_URL_BOOK}`)
      .subscribe(bookList => {
        let count = 0;
        bookList.forEach(book =>{
          count += book.copies
        })
        this.totalBooks = '' + count;
      }, (err) => {
        this.toastr.error("Can't fetch books: " + err.statusText, 'Error');
      })
  }


}
