import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { LoginDTO } from '../dtos/login.dto';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string;
  password: string;

  constructor(private router: Router, private userService: UserService){
    this.phoneNumber = '556677';
    this.password = '12345';
    //inject

  }

  onPhoneNumberChange(){
    console.log(`Phone typed: ${this.phoneNumber}`)
    //how to validate ? phone must be at least 6 characters
  }
  login() {
    const message = `phone: ${this.phoneNumber}`+
                    `password: ${this.password}`
                   ;
    //alert(message);
    debugger
    
    const loginDTO:LoginDTO = {
        "phone_number": this.phoneNumber,
        "password": this.password,
    }
    this.userService.login(loginDTO).subscribe({
        next: (response: any) => {
          debugger
          this.router.navigate(['/login']);          
        },
        complete: () => {
          debugger
        },
        error: (error: any) => {          
          alert(`Cannot login, error: ${error.error}`)          
        }
    })   
  }
}
