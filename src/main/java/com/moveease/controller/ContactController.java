package com.moveease.controller;

import com.moveease.model.ContactMessage;
import com.moveease.service.ContactMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactMessageService contactMessageService;

    @Autowired
    public ContactController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    public ResponseEntity<ContactMessage> sendMessage(@Valid @RequestBody ContactMessage message) {
        ContactMessage saved = contactMessageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllMessages() {
        return ResponseEntity.ok(contactMessageService.getAllMessages());
    }

    @GetMapping("/unread")
    public ResponseEntity<List<ContactMessage>> getUnread() {
        return ResponseEntity.ok(contactMessageService.getUnreadMessages());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ContactMessage> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(contactMessageService.markAsRead(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        contactMessageService.deleteMessage(id);
        return ResponseEntity.ok(Map.of("message", "Message deleted"));
    }
}
