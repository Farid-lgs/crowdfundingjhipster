import { Injectable } from "@angular/core";

@Injectable()
export class GlobalVariables {

  personalSubbarBtns: Array<{logo: string, txt: string}> = [{logo: '', txt:'Détails'}, {logo: '', txt:'Authentification'}, {logo: '', txt:'Mot de passe'}, {logo: '', txt:'Carte de crédit'}, {logo: '', txt:'Adresse'}, {logo: '', txt:'Projets'}, {logo: '', txt:'Communautés'}];

  communitySubbarBtns: Array<{logo: string, txt: string}> = [{logo: '', txt:'Settings'}, {logo: '', txt:'Member request'}, {logo: '', txt:'Members'}];

  projectSubbarBtns: Array<{logo: string, txt: string}> = [{logo: '', txt:'Settings'}, {logo: '', txt:'Description'}, {logo: '', txt:'Budget'}, {logo: '', txt:'Account'}, {logo: '', txt:'Rewards'}];
}
export default GlobalVariables;
