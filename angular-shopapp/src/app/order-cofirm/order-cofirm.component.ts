import { Component } from '@angular/core';
import { HeaderComponent } from "../header/header.component";
import { FooterComponent } from "../footer/footer.component";

@Component({
    selector: 'app-order-cofirm',
    standalone: true,
    templateUrl: './order-cofirm.component.html',
    styleUrl: './order-cofirm.component.scss',
    imports: [HeaderComponent, FooterComponent]
})
export class OrderCofirmComponent {

}
