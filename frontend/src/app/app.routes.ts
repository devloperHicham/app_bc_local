import { Routes } from '@angular/router';
import { PageNotFoundComponent } from './template/page-not-found/page-not-found.component';
import { LoginComponent } from './auth/login/login.component';
import { EditPasswordComponent } from './auth/edit-password/edit-password.component';
import { AuthGuard } from './auth/guards/auth.guard';
import { roleRedirectGuard } from './auth/guards/role-redirect.guard';

export const routes: Routes = [
  {
    path: '',
    title: 'Accueil',
    pathMatch: 'full',
    canActivate: [roleRedirectGuard], // Apply the guard
  },
  //********************** * to `Login` * ***********************
  {
    path: 'login',
    title: 'Authentification',
    component: LoginComponent,
  },
  {
    path: 'edit-password/:id',
    title: 'Modifier mot de passe',
    component: EditPasswordComponent,
    canActivate: [AuthGuard],
  },
  //********************** * to admin module * ***********************
  //**************************************************************** */
  //********************** * companies * ***********************
  {
    path: 'companies',
    loadComponent: () =>
      import(
        './admin/companies/index-companies/index-companies.component'
      ).then((c) => c.IndexCompaniesComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-companies',
    loadComponent: () =>
      import('./admin/companies/add-companies/add-companies.component').then(
        (c) => c.AddCompaniesComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-companies/:id',
    loadComponent: () =>
      import('./admin/companies/edit-companies/edit-companies.component').then(
        (c) => c.EditCompaniesComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * containers * ***********************
  {
    path: 'containers',
    loadComponent: () =>
      import(
        './admin/containers/index-containers/index-containers.component'
      ).then((c) => c.IndexContainersComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-containers',
    loadComponent: () =>
      import('./admin/containers/add-containers/add-containers.component').then(
        (c) => c.AddContainersComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-containers/:id',
    loadComponent: () =>
      import(
        './admin/containers/edit-containers/edit-containers.component'
      ).then((c) => c.EditContainersComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * gargo * ***********************
  {
    path: 'gargos',
    loadComponent: () =>
      import('./admin/gargo/index-gargo/index-gargo.component').then(
        (c) => c.IndexGargoComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-gargos',
    loadComponent: () =>
      import('./admin/gargo/add-gargo/add-gargo.component').then(
        (c) => c.AddGargoComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-gargos/:id',
    loadComponent: () =>
      import('./admin/gargo/edit-gargo/edit-gargo.component').then(
        (c) => c.EditGargoComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * ports * ***********************
  {
    path: 'ports',
    loadComponent: () =>
      import('./admin/ports/index-port/index-port.component').then(
        (c) => c.IndexPortComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-ports',
    loadComponent: () =>
      import('./admin/ports/add-port/add-port.component').then(
        (c) => c.AddPortComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-ports/:id',
    loadComponent: () =>
      import('./admin/ports/edit-port/edit-port.component').then(
        (c) => c.EditPortComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * transportations * ***********************
  {
    path: 'transportations',
    loadComponent: () =>
      import(
        './admin/transportations/index-transportations/index-transportations.component'
      ).then((c) => c.IndexTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-transportations',
    loadComponent: () =>
      import(
        './admin/transportations/add-transportations/add-transportations.component'
      ).then((c) => c.AddTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-transportations/:id',
    loadComponent: () =>
      import(
        './admin/transportations/edit-transportations/edit-transportations.component'
      ).then((c) => c.EditTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * comparison * ***********************
  {
    path: 'transportations',
    loadComponent: () =>
      import(
        './admin/transportations/index-transportations/index-transportations.component'
      ).then((c) => c.IndexTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-transportations',
    loadComponent: () =>
      import(
        './admin/transportations/add-transportations/add-transportations.component'
      ).then((c) => c.AddTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-transportations/:id',
    loadComponent: () =>
      import(
        './admin/transportations/edit-transportations/edit-transportations.component'
      ).then((c) => c.EditTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * transportations * ***********************
  {
    path: 'transportations',
    loadComponent: () =>
      import(
        './admin/transportations/index-transportations/index-transportations.component'
      ).then((c) => c.IndexTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-transportations',
    loadComponent: () =>
      import(
        './admin/transportations/add-transportations/add-transportations.component'
      ).then((c) => c.AddTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-transportations/:id',
    loadComponent: () =>
      import(
        './admin/transportations/edit-transportations/edit-transportations.component'
      ).then((c) => c.EditTransportationsComponent),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },

  {
    path: 'users',
    loadComponent: () =>
      import('./admin/users/index-users/index-users.component').then(
        (c) => c.IndexUsersComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'add-users',
    loadComponent: () =>
      import('./admin/users/add-users/add-users.component').then(
        (c) => c.AddUsersComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'edit-users/:id',
    loadComponent: () =>
      import('./admin/users/edit-users/edit-users.component').then(
        (c) => c.EditUsersComponent
      ),
    canActivate: [AuthGuard],
    data: { role: 'ADMIN' },
  },
  //********************** * User Module  * ***********************
  /************************************************************ */
  //********************** * comparison  * ***********************
  {
    path: 'comparisons',
    loadComponent: () =>
      import(
        './users/comparison/index-comparison/index-comparison.component'
      ).then((c) => c.IndexComparisonComponent),
    canActivate: [AuthGuard],
  },
  {
    path: 'add-comparisons',
    loadComponent: () =>
      import('./users/comparison/add-comparison/add-comparison.component').then(
        (c) => c.AddComparisonComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'edit-comparisons/:id',
    loadComponent: () =>
      import(
        './users/comparison/edit-comparison/edit-comparison.component'
      ).then((c) => c.EditComparisonComponent),
    canActivate: [AuthGuard],
  },
  {
    path: 'historique-comparisons',
    loadComponent: () =>
      import(
        './users/comparison/historique-comparison/historique-comparison.component'
      ).then((c) => c.HistoriqueComparisonComponent),
    canActivate: [AuthGuard],
  },
  //********************** * schedules * ***********************
  {
    path: 'schedules',
    loadComponent: () =>
      import('./users/schedules/index-schedule/index-schedule.component').then(
        (c) => c.IndexScheduleComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'add-schedules',
    loadComponent: () =>
      import('./users/schedules/add-schedule/add-schedule.component').then(
        (c) => c.AddScheduleComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'edit-schedules/:id',
    loadComponent: () =>
      import('./users/schedules/edit-schedule/edit-schedule.component').then(
        (c) => c.EditScheduleComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'historique-schedules',
    loadComponent: () =>
      import(
        './users/schedules/historique-schedule/historique-schedule.component'
      ).then((c) => c.HistoriqueScheduleComponent),
    canActivate: [AuthGuard],
  },
  //********************** * faq * ***********************
  {
    path: 'faqs',
    loadComponent: () =>
      import('./users/faq/index-faq/index-faq.component').then(
        (c) => c.IndexFaqComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'add-faqs',
    loadComponent: () =>
      import('./users/faq/add-faq/add-faq.component').then(
        (c) => c.AddFaqComponent
      ),
    canActivate: [AuthGuard],
  },
  {
    path: 'edit-faqs/:id',
    loadComponent: () =>
      import('./users/faq/edit-faq/edit-faq.component').then(
        (c) => c.EditFaqComponent
      ),
    canActivate: [AuthGuard],
  },

  //********************** * home users * ***********************
  {
    path: 'home-users',
    loadComponent: () =>
      import('./template/home/home-user/home-user.component').then(
        (c) => c.HomeUserComponent
      ),
    canActivate: [AuthGuard],
  },
  //********************** * home admin * ***********************
  {
    path: 'home-admin',
    loadComponent: () =>
      import('./template/home/home-admin/home-admin.component').then(
        (c) => c.HomeAdminComponent
      ),
    canActivate: [AuthGuard],
  },
  //********************** * notifications * ***********************
  {
    path: 'notifications',
    loadComponent: () =>
      import('./users/notification/notification.component').then(
        (c) => c.NotificationComponent
      ),
    canActivate: [AuthGuard],
  },
  //********************** * page authorized * ***********************
  {
    path: 'unauthorized',
    loadComponent: () =>
      import('./auth/error/unauthorized/unauthorized.component').then(
        (c) => c.UnauthorizedComponent
      ),
  },

  { path: '**', component: PageNotFoundComponent },
];
