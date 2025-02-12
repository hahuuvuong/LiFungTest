import { Routes } from '@angular/router';
import { ContactListComponent } from './contact-list/contact-list.component';
import { ContactDetailComponent } from './contact-detail/contact-detail.component';

export const routes: Routes = [
  { path: '', component: ContactListComponent },
  { path: 'contact/:id', component: ContactDetailComponent }
];
