import { Component, OnInit } from '@angular/core';;
import {ActivatedRoute} from "@angular/router";
import {CreatorService} from "../../../shared/service/creator.service";
import {IProject} from "../project.model";

@Component({
  selector: 'jhi-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  project: IProject | null = null;

  constructor(protected activatedRoute: ActivatedRoute, protected creatorService: CreatorService) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.project = project;

      this.creatorService.userLogin = project.userInfos?.user?.login;
    });
  }

}
