import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MembersComponent } from './view/members/members.component';
import { BooksComponent } from './view/books/books.component';
import { IssueComponent } from './view/issue/issue.component';
import { HomeComponent } from './view/home/home.component';
import {RouterModule, Routes} from "@angular/router";


const route:Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  {
    path: 'home',
    component:HomeComponent
  },
  {
    path: 'books',
    component:BooksComponent
  },
  {
    path: 'members',
    component:MembersComponent
  },
  {
    path: 'issues',
    component:IssueComponent
  }
]

@NgModule({
  declarations: [
    AppComponent,
    MembersComponent,
    BooksComponent,
    IssueComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(route)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
