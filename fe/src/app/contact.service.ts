import { Injectable } from '@angular/core';
import {Contact} from './ contact.model';

@Injectable({ providedIn: 'root' })
export class ContactService {
  private contacts: Contact[] = [
    { id: 1, name: 'John Doe', email: 'john@example.com', phone: '123-456-7890' },
    { id: 2, name: 'Jane Smith', email: 'jane@example.com', phone: '987-654-3210' },
    { id: 3, name: 'Alice Johnson', email: 'alice@example.com', phone: '555-666-7777' }
  ];

  getContacts(): Contact[] {
    return this.contacts;
  }
}
