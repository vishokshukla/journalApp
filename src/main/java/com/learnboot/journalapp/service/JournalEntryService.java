package com.learnboot.journalapp.service;

import com.learnboot.journalapp.entity.JournalEntry;
import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry entry, String username) {
        try {
            User user = userService.findUserByUsername(username);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
            return saved;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the journal entry", e);
        }
    }

    public JournalEntry saveJournalEntry(JournalEntry entry) {
        entry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(entry);
    }

    public List<JournalEntry> findAllJournalEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findJournalEntryById(ObjectId entryId) {
        return journalEntryRepository.findById(entryId);
    }

    @Transactional
    public boolean deleteJournalEntryById(ObjectId entryId, String username) {
        boolean removed = false;
        try {
            User user = userService.findUserByUsername(username);
            removed = user.getJournalEntries().removeIf(j -> j.getId().equals(entryId));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(entryId);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the journal entry", e);
        }
        return removed;
    }

    public void deleteJournalEntryById(ObjectId entryId) {
        journalEntryRepository.deleteById(entryId);
    }

}
