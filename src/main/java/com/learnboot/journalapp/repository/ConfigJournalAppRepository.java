package com.learnboot.journalapp.repository;

import com.learnboot.journalapp.entity.ConfigJournalApp;
import com.learnboot.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalApp, ObjectId> {

}
