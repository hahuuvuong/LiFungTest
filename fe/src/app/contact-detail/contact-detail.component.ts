import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ContactService } from '../contact.service';
import { Contact } from '../ contact.model';

@Component({
  selector: 'app-contact-detail',
  templateUrl: './contact-detail.component.html',
  styleUrls: ['./contact-detail.component.css']
})
export class ContactDetailComponent implements OnInit {
  contact: Contact | undefined;

  constructor(private route: ActivatedRoute, private contactService: ContactService) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.contact = this.contactService.getContacts().find(contact => contact.id === id);
  }
}
