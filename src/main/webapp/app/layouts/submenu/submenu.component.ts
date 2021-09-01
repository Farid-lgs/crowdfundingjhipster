import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'jhi-submenu',
  templateUrl: './submenu.component.html',
  styleUrls: ['./submenu.component.scss']
})
export class SubmenuComponent implements OnInit {

  @Input() btnDatas: any;

  constructor() {
    // do nothing
  }

  ngOnInit(): void {
    // do nothing
  }

}
