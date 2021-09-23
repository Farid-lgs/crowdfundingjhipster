import { Injectable } from "@angular/core";

@Injectable()
export class GlobalVariables {

  login: string = localStorage.getItem('login') as string;
  personalSubbarBtns: Array<{path: string, logo: string, txt: string}> = [{path: `details/${this.login}/view`, logo: '', txt:'Détails'}, {path: 'project', logo: '', txt:'Projets'}, {path: 'community', logo: '', txt:'Communautés'}];

  communitySubbarBtns: Array<{path: string, logo: string, txt: string}> = [{path: 'settings', logo: '', txt:'Settings'}, {path: 'memberRequest', logo: '', txt:'Member request'}, {path: 'members', logo: '', txt:'Members'}];

  projectSubbarBtns: Array<{path: string, logo: string, txt: string}> = [{path: 'settings', logo: '', txt:'Settings'}, {path: 'description', logo: '', txt:'Description'}, {path: 'budget', logo: '', txt:'Budget'}, {path: 'account', logo: '', txt:'Account'}, {path: 'reward', logo: '', txt:'Rewards'}];
}
export default GlobalVariables;
