import { Component } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from '../ contact.model';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {FilterPipe} from '../filter.pipe';
import {CommonModule} from '@angular/common';


@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [CommonModule, FormsModule, FilterPipe],
  templateUrl: './contact-list.component.html',
  styleUrls: ['./contact-list.component.css']
})
export class ContactListComponent {
  contacts: Contact[];
  searchTerm: string = '';

  constructor(private contactService: ContactService, private router: Router) {
    this.contacts = this.contactService.getContacts();
  }

  viewContactDetails(contact: Contact) {
    this.router.navigate(['/contact', contact.id]); // Navigate to the details page
  }
}
