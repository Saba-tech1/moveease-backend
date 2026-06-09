package com.moveease.service;

import com.moveease.model.ContactMessage;
import com.moveease.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    @Autowired
    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public ContactMessage saveMessage(ContactMessage message) {
        return contactMessageRepository.save(message);
    }

    public List<ContactMessage> getAllMessages() {
        return contactMessageRepository.findAll();
    }

    public List<ContactMessage> getUnreadMessages() {
        return contactMessageRepository.findByRead(false);
    }

    public ContactMessage markAsRead(Long id) {
        ContactMessage msg = contactMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + id));
        msg.setRead(true);
        return contactMessageRepository.save(msg);
    }

    public void deleteMessage(Long id) {
        contactMessageRepository.deleteById(id);
    }
}
