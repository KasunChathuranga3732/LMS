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
  copies: string = '';

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

  saveBook(txtIsbn: HTMLInputElement, txtTitle: HTMLInputElement, txtAuthor: HTMLInputElement,
           txtCopies: HTMLInputElement) {
    if(!this.validateData(txtIsbn, txtTitle, txtAuthor, txtCopies)){
      return;
    }

    const book = new Book(this.isbn, this.title, this.author, +this.copies);

    if(this.btnText === 'Save Book'){
      this.http.post(`${this.API_BASE_URL}`, book)
        .subscribe(result =>{
          this.toastr.success('Successfully save the book', 'Success');
          this.bookList.push(book);
          this.resetForm(txtIsbn, txtTitle, txtAuthor, txtCopies, true);
          txtIsbn.focus();
        }, (err) => {
          this.toastr.error(err.error, 'Error');
        })
    }
  }

  private validateData(txtIsbn: HTMLInputElement, txtTitle: HTMLInputElement, txtAuthor: HTMLInputElement,
                       txtCopies: HTMLInputElement) {
    let valid = true;
    this.resetForm(txtIsbn, txtTitle, txtAuthor, txtCopies, false);

    if(!this.copies){
      valid = this.invalidate(txtCopies, "Copies can't be empty");
    } else if(!/^\d+$/.test(this.copies)){
      valid = this.invalidate(txtCopies, "Copies should be positive integer");
    }
    if(!this.author){
      valid = this.invalidate(txtAuthor, "Author can't be empty");
    }

    if(!this.title){
      valid = this.invalidate(txtTitle, "Title can't be empty");
    }

    if(!this.isbn){
      valid = this.invalidate(txtIsbn, "ISBN can't be empty");
    } else if(!/^\d{13}$/.test(this.isbn)){
      valid = this.invalidate(txtIsbn, "Invalid isbn");
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
