import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-submenu',
  templateUrl: './submenu.component.html',
  styleUrls: ['./submenu.component.scss']
})
export class SubmenuComponent implements OnInit {

  @Input() btnDatas: any;
  userLogin: string = localStorage.getItem('login') as string;

  constructor() {
    // do nothing
  }

  ngOnInit(): void {
    this.userLogin = localStorage.getItem('login') as string;
    // do nothing
  }

}
