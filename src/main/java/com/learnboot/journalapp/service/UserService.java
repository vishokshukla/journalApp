package com.learnboot.journalapp.service;

import com.learnboot.journalapp.entity.User;
import com.learnboot.journalapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User entry) {
        return userRepository.save(entry);
    }

    public boolean saveNewUser(User entry) {
        try {
            entry.setPassword(passwordEncoder.encode(entry.getPassword()));
            entry.setRoles(Arrays.asList("USER"));
            userRepository.save(entry);
            return true;
        } catch (Exception e) {
            log.error("Error saving user");
            log.warn("warn saving user");
            log.info("info saving user");
            return false;
        }
    }

    public User saveAdmin(User entry) {
        entry.setPassword(passwordEncoder.encode(entry.getPassword()));
        entry.setRoles(Arrays.asList("USER", "ADMIN"));
        return userRepository.save(entry);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(ObjectId entryId) {
        return userRepository.findById(entryId);
    }

    public void deleteUserById(ObjectId entryId) {
        userRepository.deleteById(entryId);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
