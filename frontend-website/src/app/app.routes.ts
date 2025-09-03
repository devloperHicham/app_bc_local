import { Routes } from '@angular/router';
import { PageNotFound } from './template/page-not-found/page-not-found';
import { Home } from './template/home/home/home';

export const routes: Routes = [
  { path: '', title: 'Accueil', pathMatch: 'full', redirectTo: 'home' },
  {
    path: 'home',
    title: 'Accueil',
    component: Home,
  },
  {
    path: 'whishlist-booking',
    loadComponent: () =>
      import('./template/whishlists/whishlist-booking/whishlist-booking').then(
        (c) => c.WhishlistBooking
      ),
  },
  {
    path: 'about-us',
    loadComponent: () =>
      import('./template/about-us/about-us/about-us').then((c) => c.AboutUs),
  },
  {
    path: 'prices',
    loadComponent: () =>
      import('./template/prices/price/price').then((c) => c.Price),
  },
  {
    path: 'search-sched-results',
    loadComponent: () =>
      import(
        './template/searchSchedule/search-sched-result/search-sched-result'
      ).then((c) => c.SearchSchedResult),
  },
  {
    path: 'search-comp-results',
    loadComponent: () =>
      import(
        './template/searchComparison/search-comp-result/search-comp-result'
      ).then((c) => c.SearchCompResult),
  },
  {
    path: 'comming-soon',
    loadComponent: () =>
      import('./template/services/comming-soon/comming-soon').then(
        (c) => c.CommingSoon
      ),
  },
  {
    path: 'trip-comp-details',
    loadComponent: () =>
      import(
        './template/searchComparison/trip-comp-detail/trip-comp-detail'
      ).then((c) => c.TripCompDetail),
  },
  {
    path: 'cargo-comp-details',
    loadComponent: () =>
      import(
        './template/searchComparison/cargo-comp-detail/cargo-comp-detail'
      ).then((c) => c.CargoCompDetail),
  },
  {
    path: 'quote-lists',
    loadComponent: () =>
      import('./template/quotes/list-quote/list-quote').then(
        (c) => c.ListQuote
      ),
  },
  {
    path: 'manage-script',
    loadComponent: () =>
      import('./template/packs/manage-script/manage-script').then(
        (c) => c.ManageScript
      ),
  },
  {
    path: 'help-centers',
    loadComponent: () =>
      import('./template/supports/help/help').then((c) => c.Help),
  },
  {
    path: 'contacts',
    loadComponent: () =>
      import('./template/supports/contact/contact').then((c) => c.Contact),
  },
  {
    path: 'profiles',
    loadComponent: () => import('./admin/profil/profil').then((c) => c.Profil),
  },
  {
    path: 'login',
    loadComponent: () => import('./auth/login/login').then((c) => c.Login),
  },
  {
    path: 'inscription-users',
    loadComponent: () =>
      import('./admin/inscription/inscription').then((c) => c.Inscription),
  },
  {
    path: 'faq-general',
    loadComponent: () =>
      import('./template/home/faq-general/faq-general').then((c) => c.FaqGeneral),
  },
  {
    path: 'payment-orders',
    loadComponent: () =>
      import('./template/prices/payment/payment').then((c) => c.Payment),
  },
  {
    path: 'terms-services',
    loadComponent: () =>
      import('./template/policy/terms-service/terms-service').then((c) => c.TermsService),
  },{
    path: 'cancellation-conditions',
    loadComponent: () =>
      import('./template/policy/cancellation-conditions/cancellation-conditions').then((c) => c.CancellationConditions),
  },{
    path: 'payment-conditions',
    loadComponent: () =>
      import('./template/policy/payment-conditions/payment-conditions').then((c) => c.PaymentConditions),
  },{
    path: 'privacy-policies',
    loadComponent: () =>
      import('./template/policy/privacy-policy/privacy-policy').then((c) => c.PrivacyPolicy),
  },{
    path: 'conditions-dispute',
    loadComponent: () =>
      import('./template/policy/conditions-dispute/conditions-dispute').then((c) => c.ConditionsDispute),
  },
  { path: '**', component: PageNotFound, title: 'Erreur' },
];
