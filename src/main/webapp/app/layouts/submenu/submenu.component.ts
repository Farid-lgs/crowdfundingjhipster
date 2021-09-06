import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-submenu',
  templateUrl: './submenu.component.html',
  styleUrls: ['./submenu.component.scss']
})
export class SubmenuComponent implements OnInit {

  @Input() btnDatas: any;
  userLogin: string = localStorage.getItem('user') as string;
  // test: string = localStorage.getItem('user') === null ? '' : localStorage.getItem('user');

  constructor() {
    // do nothing
  }

  ngOnInit(): void {
    // if(localStorage.getItem('user')  null)
    //   this.user = <string>localStorage.getItem('user') === null ? '' : localStorage.getItem('user');
    // // do nothing
  }

}
