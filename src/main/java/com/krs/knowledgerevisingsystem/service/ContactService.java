package com.krs.knowledgerevisingsystem.service;

import com.krs.knowledgerevisingsystem.entity.Contact;
import com.krs.knowledgerevisingsystem.entity.User;
import com.krs.knowledgerevisingsystem.exception.ManageUser.UserInvalidException;

import java.util.List;

public interface ContactService {
    Boolean saveContact(String name, String email, String phone, String message, User admin);
    Boolean deleteContact(Long contactId);
    List<Contact> getAllContact();
    List<Contact> getAllContactByAdminId(Long userId);
}
