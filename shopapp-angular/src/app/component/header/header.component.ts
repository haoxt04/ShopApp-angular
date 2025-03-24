import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { UserResponse } from '../../response/user/user.reponse';
import { TokenService } from '../../service/token.service';
import { NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-header',
  standalone: false,
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit{
  userResponse?:UserResponse | null;
  isPopoverOpen = false;

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopoverOpen = !this.isPopoverOpen;
  }

  handleItemClick(index: number): void {
    //alert(`Clicked on "${index}"`);
    if(index === 2) {
      this.userService.removeUserFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserResponseFromLocalStorage();    
    }
    this.isPopoverOpen = false; // Close the popover after clicking an item    
  }

  constructor(
    private userService: UserService,   
    private popoverConfig: NgbPopoverConfig,  
    private tokenService: TokenService  
  ) {
    
   }
  ngOnInit() {
    this.userResponse = this.userService.getUserResponseFromLocalStorage();    
  }  
}
