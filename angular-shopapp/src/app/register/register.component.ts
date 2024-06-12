import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-register',
    standalone: true,
    templateUrl: './register.component.html',
    styleUrl: './register.component.scss',
    imports: [HeaderComponent, FooterComponent,CommonModule, FormsModule]
})
export class RegisterComponent {
    phoneNumber : string;
    password: string;
    retypePassword: string;
    fullName: string;
    address: string;
    isAccepted: boolean;

    constructor(){
        this.phoneNumber = '';
        this.password = '';
        this.retypePassword = '';
        this.fullName = '';
        this.address = '';
        this.isAccepted = false;
    }
    onPhoneNumberChange(){
        console.log(`phone  + ${this.phoneNumber}`)
    }
    register(){
        const message = `phone : ${this.phoneNumber}`
        alert('you need register')
    }
    checkPasswordsMatch(){
        console.log(`${this.password}`)
    }
}
