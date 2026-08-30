package com.learnboot.journalapp.controller;

import com.learnboot.journalapp.entity.JournalEntry;
import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.service.JournalEntryService;

import com.learnboot.journalapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal Entry Apis", description = "Read/Update/Delete Journal Entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public ResponseEntity<List<JournalEntry>> getAll() {
//        List<JournalEntry> allJournalEntry = journalEntryService.findAllJournalEntry();
//        if (allJournalEntry != null && !allJournalEntry.isEmpty()) {
//            return new ResponseEntity<>(allJournalEntry, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<JournalEntry> allJournalEntry = user.getJournalEntries();
        if (allJournalEntry != null && !allJournalEntry.isEmpty()) {
            return new ResponseEntity<>(allJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{entryId}")
    @Operation(summary = "Get All Journal Entries of an User")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String entryId) {
        ObjectId id = new ObjectId(entryId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userByUsername = userService.findUserByUsername(username);
        List<JournalEntry> collect = userByUsername.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> entry = journalEntryService.findJournalEntryById(id);
            if (entry.isPresent())
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping
//    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry) {
//        try {
//            JournalEntry entry = journalEntryService.saveJournalEntry(journalEntry);
//            return new ResponseEntity<>(entry, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            JournalEntry entry = journalEntryService.saveJournalEntry(journalEntry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("id/{entryId}")
//    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId entryId, @RequestBody JournalEntry journalEntry) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User userByUsername = userService.findUserByUsername(username);
//        List<JournalEntry> collect = userByUsername.getJournalEntries().stream().filter(x -> x.getId().equals(entryId)).collect(Collectors.toList());
//        if (collect != null && collect.isEmpty()) {
//            if (jEntry != null) {
//                jEntry.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : jEntry.getTitle());
//                jEntry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : jEntry.getContent());
//                return new ResponseEntity<>(jEntry, HttpStatus.OK);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping("id/{entryId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable String entryId, @RequestBody JournalEntry journalEntry) {
        ObjectId id = new ObjectId(entryId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userByUsername = userService.findUserByUsername(username);
        List<JournalEntry> collect = userByUsername.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> jEntry = journalEntryService.findJournalEntryById(id);
            if (jEntry.isPresent()) {
                JournalEntry entry = jEntry.get();
                entry.setTitle(!journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : entry.getTitle());
                entry.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ? journalEntry.getContent() : entry.getContent());
                journalEntryService.saveJournalEntry(jEntry.orElse(null));
                return new ResponseEntity<>(entry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("id/{entryId}")
//    public ResponseEntity<Object> deleteJournalEntry(@PathVariable ObjectId entryId) {
//        journalEntryService.deleteJournalEntryById(entryId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping("id/{entryId}")
    public ResponseEntity<Object> deleteJournalEntry(@PathVariable String entryId) {
        ObjectId id = new ObjectId(entryId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteJournalEntryById(id, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
