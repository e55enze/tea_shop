package com.project.teashop.service;

import com.project.teashop.entity.User;
import com.project.teashop.repository.UserRepos;
import com.project.teashop.security.Sha256PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepos userRepos; // Репозиторий для работы с БД
    private final Sha256PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepos userRepos) {
        this.userRepos = userRepos;
        this.passwordEncoder = new Sha256PasswordEncoder(); // Внедрение нашего PasswordEncoder
    }


    @Transactional
    public User registerUser(String username, String password) {
        if (userRepos.findByUsername(username).isPresent()) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        return userRepos.save(newUser);
    }

    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepos.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}