import { Component } from '@angular/core';
import {Book} from "../../dto/book";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent {
  bookList: Array<Book> = [];
  btnText: string = 'Save Book';
  API_BASE_URL: string = 'http://localhost:8080/api/v1/books';

  constructor(private http:HttpClient, private toastr: ToastrService) {
    http.get<Array<Book>>(`${this.API_BASE_URL}`)
      .subscribe(memberList => {
        this.bookList = memberList
      }, (err) => {
        this.toastr.error("Can't fetch books: "+err.statusText, 'Error');
      });
  }
}
