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
    this.resetForm(txtId, txtName, txtContact, txtAddress, true);
    setTimeout(()=>txtName.focus(),500)
  }

  resetForm(txtId:HTMLInputElement, txtName:HTMLInputElement, txtContact:HTMLInputElement, txtAddress:HTMLInputElement, flag:boolean){
    [txtId, txtName, txtAddress, txtContact].forEach(txt => {
      txt.classList.remove('is-invalid', 'animate__shakeX')
      if(flag) txt.value = '';
    })
  }


  deleteMember(id: number | null) {
    this.http.delete(`http://localhost:8080/api/v1/members/${id}`)
      .subscribe(result => {
        const index = this.memberList.findIndex(member => member.id == id);
        this.memberList.splice(index, 1);
      })
  }

  saveMember(txtId: HTMLInputElement, txtName: HTMLInputElement, txtAddress: HTMLInputElement, txtContact: HTMLInputElement) {
    const id = txtId.value;
    const name = txtName.value;
    const address = txtAddress.value;
    const contact = txtContact.value;
    this.validateData(txtId, txtName, txtContact, txtAddress);


  }

  validateData(txtId: HTMLInputElement, txtName: HTMLInputElement, txtContact: HTMLInputElement, txtAddress: HTMLInputElement){
    const name = txtName.value.trim();
    const address = txtAddress.value.trim();
    const contact = txtContact.value.trim();
    let valid = true;
    this.resetForm(txtId, txtName, txtContact, txtAddress, false);

    if(!address){
      valid = this.invalidate(txtAddress, "Address can't be empty")

    } else if(!/^.{3,}$/.test(address)){
      valid = this.invalidate(txtAddress, "Invalid address")
    }

    if(!contact){
      valid = this.invalidate(txtContact, "Contact can't be empty")

    } else if(!/^\d{3}-\d{7}$/.test(contact)){
      valid = this.invalidate(txtContact, "Invalid Contact number")
    }

    if(!name){
      valid = this.invalidate(txtName, "Name can't be empty")

    } else if(!/^[A-za-z ]+$/.test(name)){
      valid = this.invalidate(txtName, "Invalid name")
    }
    return valid;
  }


  invalidate(txt: HTMLInputElement, msg: string){
    setTimeout(()=>txt.classList.add('is-invalid', 'animate__shakeX'),0);
    txt.select();
    $(txt).next().text(msg);
    return false;
  }
}
