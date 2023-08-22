import {Component} from '@angular/core';
import * as $ from 'jquery';
import {Member} from "../../dto/member";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.scss']
})
export class MembersComponent {
  memberList: Array<Member> = [];

  constructor(private http:HttpClient) {
    http.get<Array<Member>>('https://localhost:8080/api/v1/members')
      .subscribe(memberList => this.memberList = memberList);
  }


  newMember(txtId: HTMLInputElement, txtName: HTMLInputElement, txtContact: HTMLInputElement, txtAddress: HTMLInputElement) {
    // $('#new-customer-modal').removeClass('fade');
    $('#new-customer-modal').trigger
    this.resetForm(txtId, txtName, txtContact, txtAddress);
    setTimeout(()=>txtName.focus(),500)
  }

  resetForm(txtId:HTMLInputElement, txtName:HTMLInputElement, txtContact:HTMLInputElement, txtAddress:HTMLInputElement){
    [txtId, txtName, txtAddress, txtContact].forEach(txt => {
      txt.classList.remove('is-invalid', 'animate__shakeX')
      txt.value = '';
    })
  }


  deleteMember(id: number | null) {
    this.http.delete(`http://localhost:8080/api/v1/members/${id}`)
      .subscribe(result => {
        const index = this.memberList.findIndex(member => member.id == id);
        this.memberList.splice(index, 1);
      })
  }
}
