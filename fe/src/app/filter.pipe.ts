import { Pipe, PipeTransform } from '@angular/core';
import {Contact} from './ contact.model';


@Pipe({standalone: true, name: 'filter'})
export class FilterPipe implements PipeTransform {
  transform(contacts: Contact[], searchTerm: string): Contact[] {
    if (!searchTerm) return contacts;
    return contacts.filter(contact =>
      contact.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
  }
}
