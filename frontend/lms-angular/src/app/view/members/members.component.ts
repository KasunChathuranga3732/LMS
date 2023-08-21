import {Component} from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-members',
  templateUrl: './members.component.html',
  styleUrls: ['./members.component.scss']
})
export class MembersComponent {


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


}
