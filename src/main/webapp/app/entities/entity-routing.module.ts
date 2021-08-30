import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        data: { pageTitle: 'crowdFundingJHipsterApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'balance-transfer',
        data: { pageTitle: 'crowdFundingJHipsterApp.balanceTransfer.home.title' },
        loadChildren: () => import('./balance-transfer/balance-transfer.module').then(m => m.BalanceTransferModule),
      },
      {
        path: 'community',
        data: { pageTitle: 'crowdFundingJHipsterApp.community.home.title' },
        loadChildren: () => import('./community/community.module').then(m => m.CommunityModule),
      },
      {
        path: 'community-members',
        data: { pageTitle: 'crowdFundingJHipsterApp.communityMembers.home.title' },
        loadChildren: () => import('./community-members/community-members.module').then(m => m.CommunityMembersModule),
      },
      {
        path: 'community-notifications',
        data: { pageTitle: 'crowdFundingJHipsterApp.communityNotifications.home.title' },
        loadChildren: () => import('./community-notifications/community-notifications.module').then(m => m.CommunityNotificationsModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'crowdFundingJHipsterApp.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'contribution',
        data: { pageTitle: 'crowdFundingJHipsterApp.contribution.home.title' },
        loadChildren: () => import('./contribution/contribution.module').then(m => m.ContributionModule),
      },
      {
        path: 'contribution-notifications',
        data: { pageTitle: 'crowdFundingJHipsterApp.contributionNotifications.home.title' },
        loadChildren: () =>
          import('./contribution-notifications/contribution-notifications.module').then(m => m.ContributionNotificationsModule),
      },
      {
        path: 'country',
        data: { pageTitle: 'crowdFundingJHipsterApp.country.home.title' },
        loadChildren: () => import('./country/country.module').then(m => m.CountryModule),
      },
      {
        path: 'credit-card',
        data: { pageTitle: 'crowdFundingJHipsterApp.creditCard.home.title' },
        loadChildren: () => import('./credit-card/credit-card.module').then(m => m.CreditCardModule),
      },
      {
        path: 'project',
        data: { pageTitle: 'crowdFundingJHipsterApp.project.home.title' },
        loadChildren: () => import('./project/project.module').then(m => m.ProjectModule),
      },
      {
        path: 'project-images',
        data: { pageTitle: 'crowdFundingJHipsterApp.projectImages.home.title' },
        loadChildren: () => import('./project-images/project-images.module').then(m => m.ProjectImagesModule),
      },
      {
        path: 'project-account',
        data: { pageTitle: 'crowdFundingJHipsterApp.projectAccount.home.title' },
        loadChildren: () => import('./project-account/project-account.module').then(m => m.ProjectAccountModule),
      },
      {
        path: 'project-comment',
        data: { pageTitle: 'crowdFundingJHipsterApp.projectComment.home.title' },
        loadChildren: () => import('./project-comment/project-comment.module').then(m => m.ProjectCommentModule),
      },
      {
        path: 'reward',
        data: { pageTitle: 'crowdFundingJHipsterApp.reward.home.title' },
        loadChildren: () => import('./reward/reward.module').then(m => m.RewardModule),
      },
      {
        path: 'user-infos',
        data: { pageTitle: 'crowdFundingJHipsterApp.userInfos.home.title' },
        loadChildren: () => import('./user-infos/user-infos.module').then(m => m.UserInfosModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
