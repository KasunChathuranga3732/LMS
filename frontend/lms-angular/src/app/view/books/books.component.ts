import { Component } from '@angular/core';
import {Book} from "../../dto/book";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import * as $ from "jquery";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent {
  bookList: Array<Book> = [];
  btnText: string = 'Save Book';
  API_BASE_URL: string = 'http://localhost:8080/api/v1/books';
  isbn: string = '';
  title: string = '';
  author: string = '';
  copies: string = '0';

  constructor(private http:HttpClient, private toastr: ToastrService) {
    http.get<Array<Book>>(`${this.API_BASE_URL}`)
      .subscribe(memberList => {
        this.bookList = memberList
      }, (err) => {
        this.toastr.error("Can't fetch books: "+err.statusText, 'Error');
      });
  }

  getBooks(txtSearch: HTMLInputElement) {
    const searchText = txtSearch.value.trim();
    const query = (searchText) ? `?q=${searchText}`: "";
    this.http.get<Array<Book>>(`${this.API_BASE_URL}` + query)
      .subscribe(bookList => {
        this.bookList = bookList
      }, (err) => {
        this.toastr.error("Can't fetch books: "+err.statusText, 'Error');
      });
  }

  saveBook(txtIsbn: HTMLInputElement, txtTitle: HTMLInputElement, txtAuthor: HTMLInputElement,
           txtCopies: HTMLInputElement) {

  }

  newBook(txtIsbn: HTMLInputElement, txtTitle: HTMLInputElement, txtAuthor: HTMLInputElement,
          txtCopies: HTMLInputElement) {
    $('#new-customer-modal').trigger;
    this.btnText = 'Save Book';
    txtIsbn.removeAttribute('disabled');
    this.resetForm(txtIsbn, txtTitle, txtAuthor, txtCopies, true);
    setTimeout(()=>txtIsbn.focus(),500);
  }

  private resetForm(txtIsbn: HTMLInputElement, txtTitle: HTMLInputElement, txtAuthor: HTMLInputElement,
                    txtCopies: HTMLInputElement, flag: boolean) {
    [txtIsbn, txtTitle, txtAuthor, txtCopies].forEach(txt => {
      txt.classList.remove('is-invalid', 'animate__shakeX')
      if(flag) txt.value = '';
    })
  }
}
