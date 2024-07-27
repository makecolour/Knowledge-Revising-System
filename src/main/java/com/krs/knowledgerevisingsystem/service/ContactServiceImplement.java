package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Contact;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.UserInvalidException;
import com.krs.knowledgerevisingsystem.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImplement implements ContactService{
    private final ContactRepository contactRepository;
    private final UserService userService;

    public ContactServiceImplement(ContactRepository contactRepository, UserService userService) {
        this.contactRepository = contactRepository;
        this.userService = userService;
    }

    @Override
    public Boolean saveContact(String name, String email, String phone, String message, User admin){
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);
        contact.setMessage(message);
        contact.setUser(admin);
        contactRepository.save(contact);
        return true;
    }

    @Override
    public Boolean deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
        return true;
    }

    @Override
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> getAllContactByAdminId(Long userId) {
        return contactRepository.findAllByUserId(userId);
    }
}
