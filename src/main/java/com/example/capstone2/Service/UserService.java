package com.example.capstone2.Service;

import com.example.capstone2.Model.User;
import com.example.capstone2.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean addUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }

        if (userRepository.existsByPhone(user.getPhone())) {
            return false;
        }

        userRepository.save(user);
        return true;
    }


    public boolean updateUser(Integer id, User newUser) {

        User oldUser = userRepository.findUserById(id);

        if (oldUser == null) {
            return false;
        }

        if (!oldUser.getEmail().equals(newUser.getEmail())
                && userRepository.existsByEmail(newUser.getEmail())) {
            return false;
        }

        if (!oldUser.getPhone().equals(newUser.getPhone())
                && userRepository.existsByPhone(newUser.getPhone())) {
            return false;
        }

        oldUser.setName(newUser.getName());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setPhone(newUser.getPhone());

        userRepository.save(oldUser);

        return true;
    }

    public boolean deleteUser(Integer id) {

        User user = userRepository.findUserById(id);

        if (user == null) {
            return false;
        }

        userRepository.delete(user);
        return true;
    }

    public User login(String email, String password) {

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        emailService.sendLoginNotification(
                user.getEmail(),
                user.getName()
        );

        return user;
    }

}
