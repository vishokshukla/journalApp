package com.learnboot.journalapp.repository;

import com.learnboot.journalapp.entity.JournalEntry;
import com.learnboot.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUsername(String username);

    void deleteByUsername(String username);
}
